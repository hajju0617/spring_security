package com.green.greengram.user;

import com.green.greengram.common.AppProperties;
import com.green.greengram.common.CookieUtils;
import com.green.greengram.common.CustomFileUtils;
import com.green.greengram.common.MyCommonUtils;
import org.springframework.security.core.userdetails.UserDetails;
import com.green.greengram.exception.CustomException;
import com.green.greengram.exception.MemberErrorCode;
import com.green.greengram.security.*;
import com.green.greengram.security.jwt.JwtTokenProviderV2;
import com.green.greengram.user.model.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserMapper mapper;
    private final CustomFileUtils customFileUtils;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProviderV2 jwtTokenProvider;
    private final CookieUtils cookieUtils;
    private final AuthenticationFacade authenticationFacade;
    private final AppProperties appProperties;

    // SecurityContextHolder -> Context -> Authentication(UsernamePasswordAuthenticationToken) -> MyUserDetails -> MyUser
    // UsernamePasswordAuthenticationToken 여기에 값이 있어야 인증 됐다라는 의미 (JwtTokenProviderVw 103라인)

    @Transactional
    public int postSignUp(MultipartFile pic, SignUpPostReq p) {
        log.info("pic : {}",pic);
        p.setProviderType(SignInProviderType.LOCAL);
        String saveFileName = customFileUtils.makeRandomFileName(pic);
        log.info("saveFileName : {}",saveFileName);
        p.setPic(saveFileName);

        String password = passwordEncoder.encode(p.getUpw());
//        String hashedPw = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
        p.setUpw(password);

        int result = mapper.postUser(p);
        log.info("p : {}", p);

        if(pic == null) {
            return result;
        }
        try {
            String path = String.format("user/%d", p.getUserId());
            customFileUtils.makeFolders(path);
            log.info("path : {}", path);

            String target = String.format("%s/%s", path, saveFileName);
            customFileUtils.transferTo(pic, target);
            log.info("target : {}",target);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("파일 저장 오류");
        }
        return result;
    }

    public SignInRes postSignIn(HttpServletResponse res, SignInPostReq p) { // dispatcher servlet이 res (res 래퍼런스 변수) 를 준다 ( 어차피 servlet이 주소값을 가지고 있는 변수를 주므로 service에서 return 값으로 res를 주지 않아도 된다)
        p.setProviderType(SignInProviderType.LOCAL.name());
        List<UserInfo> userInfoList = mapper.getUserById(p);

        UserInfoRoles userInfoRoles = MyCommonUtils.convertToUserInfoRoles(userInfoList);

        if (userInfoRoles == null || !passwordEncoder.matches(p.getUpw(), userInfoRoles.getUpw())) {
            throw new CustomException(MemberErrorCode.INCORRECT_ID_PW);
        }


        MyUser myUser = MyUser.builder()
                .userId(userInfoRoles.getUserId())   // pk값 담고
                .roles(userInfoRoles.getRoles())          // 권한 담음
                .build();
        // => 빌더 패턴으로 객체 생성


        // access, refresh token에 myUser(유저pk, 권한 정보)를 담는다 why? -> 프론트가 accessToken을 계속 백엔드로 요청을 보낼 때, Header에 넣어서 보내준다
        // refresh token 에 myUser정보를 넣는 이유는 access token을 재발급 받을 때 access token에 myUser를 담기 위해서 담는다.
        // 요청이 올 때마다 request에 token이 담겨져 있는 지 체크 (JwtAuthenticationFilter 에서 체크한다)
        // token에 담겨져 있는 myUser를 빼내서 사용하기 위해 myUser를 담았다.
        String accessToken = jwtTokenProvider.generateAccessToken(myUser);
        String refreshToken = jwtTokenProvider.generateRefreshToken(myUser);

        // refreshToken은 보안 쿠키를 이용해서 처리 (프론트가 따로 작업을 하지 않아도 아래 cookie 값은 항상 넘어온다.)
        // 쿠키에 담는 부분
        int refreshTokenMaxAge = appProperties.getJwt().getRefreshTokenCookieMaxAge();
        cookieUtils.deleteCookie(res, "refresh-token");
        cookieUtils.setCookie(res, appProperties.getJwt().getRefreshTokenCookieName(), refreshToken, refreshTokenMaxAge);

        return SignInRes.builder()
                .userId(userInfoRoles.getUserId())   // 프로필 사진을 띄울때 사용 (프로필 사진 주소에 pk 값이 포함 됨)
                .nm(userInfoRoles.getNm())
                .pic(userInfoRoles.getPic())
                .accessToken(accessToken)   // 응답으로 바로 프론트한테 보내준다
                .build();
        // servlet 이 반환값을 받아서 response (HttpServletResponse) 에 담아서 응답으로 처리된다.
    }

    public Map getAccessToken(HttpServletRequest req) {
        Cookie cookie = cookieUtils.getCookie(req, appProperties.getJwt().getRefreshTokenCookieName());
        if(cookie == null) { // refresh-token 값이 쿠키에 존재 여부
            throw new RuntimeException();
        }

        String refreshToken = cookie.getValue();
        if(!jwtTokenProvider.isValidateToken(refreshToken)) { //refresh-token 만료시간 체크
            throw new RuntimeException();
        }

        UserDetails auth = jwtTokenProvider.getUserDetailsFromToken(refreshToken);
        MyUser myUser = ((MyUserDetails)auth).getMyUser();

        String accessToken = jwtTokenProvider.generateAccessToken(myUser);

        Map map = new HashMap();
        map.put("accessToken", accessToken);
        return map;
    }


    public UserInfoGetRes getUserInfo(UserInfoGetReq p) {
        return mapper.selProfileUserInfo(p);
    }

    @Transactional
    public String patchProfilePic(UserProfilePatchReq p) {  // 기존  폴더 삭제
        p.setSignedUserId(authenticationFacade.getLoginUserId());

        String fileNm = customFileUtils.makeRandomFileName(p.getPic()); // 랜덤 파일명 얻어와서 fileNm에 저장
        p.setPicName(fileNm);
        mapper.updProfilePic(p);

        try {
            String midPath = String.format("user/%d", p.getSignedUserId());
//    //        String folderPath = customFileUnits.uploadPath + "/user/" + p.getSignedUserId();
//            String folderPath = String.format("%s/user/%d", customFileUtils.uploadPath, p.getSignedUserId());
//            // + 기호를 쓰기 싫다면 이렇게 수정
            String delAbsoluteFolderPath = String.format("%s/%s", customFileUtils.uploadPath, midPath);
            customFileUtils.deleteFolder(delAbsoluteFolderPath);

            customFileUtils.makeFolders(midPath);
            String filePath = String.format("%s/%s", midPath, fileNm);

            customFileUtils.transferTo(p.getPic(), filePath);   // public void transferTo(MultipartFile mf, String target) throws Exception
                                                                // 예외를 던지고 있으므로 예외처리를 해줘야 레드라인이 생기지 않는다.
        } catch (Exception e) {
            throw new RuntimeException("프로필 사진 수정 실패");
        }
        return fileNm;
    }
}

package com.green.greengram.security.oauth2;

import com.green.greengram.security.MyUser;
import com.green.greengram.security.MyUserDetails;
import com.green.greengram.security.MyUserOAuth2Vo;
import com.green.greengram.security.SignInProviderType;
import com.green.greengram.security.oauth2.userinfo.OAuth2UserInfo;
import com.green.greengram.security.oauth2.userinfo.OAuth2UserInfoFactory;
import com.green.greengram.user.UserMapper;
import com.green.greengram.user.model.SignInPostReq;
import com.green.greengram.user.model.SignUpPostReq;
import com.green.greengram.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;    // OAuth2 라이브러리 (build)
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

/*
 MyOAuth2UserService :
 OAuth2 제공자(구글, 카카오, 네이버 등)로부터 Access-Token 받은 후 loadUser 메소드가 호출이 된다. (스프링 시큐리티에 구현되어 있음)
 OAuth2 제공자로부터 사용자 정보를 가져온다. (이미 구현되어 있음. : super.loadUser(userRequest))
 OAuth2User 인터페이스를 구현한 객체(인증 객체)를 정리해서 리턴 한다. (MyOAuth2UserService 에서 해야될 작업)

  프론트에서 플랫폼 소셜 로그인 아이콘을 클릭하면(리다이렉트 정보 전달 : 로그인 완료 후 다시 돌아올 프론트 주소값)
  -> 백엔드에 요청이 간다 (사용자 : 어떤 소셜 로그인 하고 싶다 에 대한 정보가 전달) -> 백엔드는 리다이렉트 (OAuth2 제공자 로그인 화면)
  -> 해당 제공자의 아이디/비밀번호를 작성 후 로그인 처리
  -> 제공자는 인가 코드를 백엔드에게 보내준다.
  -> 백엔드는 인가 코드를 가지고 access-token을 발급 받는다.
  -> access-token 으로 사용자 정보(scope에 작성한 내용)를 받는다.
  -> 이후는 자체 로그인 처리
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class MyOAuth2UserService extends DefaultOAuth2UserService {
    private final UserMapper mapper;
    private final OAuth2UserInfoFactory oAuth2UserInfoFactory;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {    // userRequest : 소셜플랫폼의 데이터들이 들어있음

        try {
            return this.process(userRequest);
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }

    }
    private OAuth2User process(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);    // 제공자(구글, 네이버, 카카오 등)로부터 사용자 정보를 얻음

        // 각 소셜플랫폼에 맞는 enum 타입을 얻는다.
        SignInProviderType signInProviderType = SignInProviderType.valueOf(userRequest.getClientRegistration()
                                                                                        .getRegistrationId()
                                                                                        .toUpperCase()
        );

        // 규격화된 UserInfo 객체로 변환
        // oAuth2User.getAttributes() -> Data가 HashMap 객체로 변환
        OAuth2UserInfo oAuth2UserInfo = oAuth2UserInfoFactory.getOAuth2UserInfo(signInProviderType, oAuth2User.getAttributes());

        // 기존에 회원가입이 되어 있는 가 체크
        SignInPostReq signInParam = new SignInPostReq();
        signInParam.setUid(oAuth2UserInfo.getId());   // 플랫폼에서 넘어오는 유니크값 (항상 같은 값, 다른 사용자들과 구별되는 유니크값)
        signInParam.setProviderType(signInProviderType.name());   // 구글이면 구글, 네이버면 네이버, 카카오면 카카오
        User user = mapper.getUserById(signInParam);

//        MyUser myUser = new MyUser();
//        myUser.setRole("ROLE_USER");

        if(user == null) {  // 회원가입 처리
            SignUpPostReq signUpParam = new SignUpPostReq();
            signUpParam.setProviderType(signInProviderType);
            signUpParam.setUid(oAuth2UserInfo.getId());
            signUpParam.setNm(oAuth2UserInfo.getName());
            signUpParam.setPic(oAuth2UserInfo.getProfilePicUrl());

            int result = mapper.postUser(signUpParam);
            user = new User(signUpParam.getUserId()
                            , signInParam.getUid()
                            , null
                            , signUpParam.getNm()
                            , signUpParam.getPic()
                            , null
                            , null);
//            myUser.setUserId(signUpParam.getUserId());  // 회원가입 후 유저 pk값 담기

//        } else {
//            myUser.setUserId(user.getUserId());         // 이미 회원가입 된 유저 pk값 담기
        }
        MyUserOAuth2Vo myUserOAuth2Vo = new MyUserOAuth2Vo(user.getUserId(), "ROLE_USER", user.getNm(), user.getPic());

        MyUserDetails signInUser = new MyUserDetails();
        signInUser.setMyUser(myUserOAuth2Vo);
        return signInUser;
    }
}

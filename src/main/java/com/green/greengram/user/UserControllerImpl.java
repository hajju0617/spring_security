package com.green.greengram.user;

import com.green.greengram.common.model.ResultDto;
import com.green.greengram.user.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "유저 컨트롤러", description = "유저 CRUD sign-in, sign-out")
@RequestMapping("/api/user")
public class UserControllerImpl {
    private final UserServiceImpl service;

    @PostMapping("sign-up")
    @Operation(summary = "회원가입", description = "프로필 사진은 필수가 아님.")
    public ResultDto<Integer> postUser(@RequestPart(required = false) MultipartFile pic, @RequestPart SignUpPostReq p) {

        log.info("p : {}",p);
        log.info("pic : {}",pic);
        int result = service.postSignUp(pic, p);

        return ResultDto.<Integer>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("회원가입 완료")
                .resultData(result)
                .build();
    }

    @PostMapping("sign-in")
    @Operation(summary = "인증", description = "")
    public ResultDto<SignInRes> postSignIn(HttpServletResponse res, @RequestBody SignInPostReq p) {
        SignInRes result = service.postSignIn(res, p);

        return ResultDto.<SignInRes>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("인증 완료")
                .resultData(result)
                .build();
    }



    /*
        프론트는 단지 get방식으로 아무런 작업없이 단순히 요청만 하면 refresh-token이 넘어온다.
        why? -> 우리가 refresh-token을 로그인을 성공했을때 cookie에 담았기 때문 (UserServiceImpl 101번째 행) (int refreshTokenMaxAge = appProperties.getJwt().getRefreshTokenCookieMaxAge(); 포함해서 하단에 2개 행)
        cookie는 요청마다 항상 넘어온다.
    */
    @GetMapping("access-token")
    public ResultDto<Map<String, String>> getAccessToken(HttpServletRequest req) {
        Map<String, String> map = service.getAccessToken(req);

        return ResultDto.<Map<String, String>>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg("Access Token 발급")
                .resultData(map)
                .build();
    }

    @GetMapping
    public ResultDto<UserInfoGetRes> getUserInfo(@ParameterObject @ModelAttribute UserInfoGetReq p) {
        UserInfoGetRes result = service.getUserInfo(p);

        return ResultDto.<UserInfoGetRes>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(result)
                .build();
    }

    @PatchMapping(value = "pic", consumes = "multipart/form-data")
    public ResultDto<String> patchProfilePic(@ModelAttribute UserProfilePatchReq p) {
        String result = service.patchProfilePic(p);

        return ResultDto.<String>builder()
                .statusCode(HttpStatus.OK)
                .resultMsg(HttpStatus.OK.toString())
                .resultData(result)
                .build();
    }

}

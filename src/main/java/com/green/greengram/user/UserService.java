package com.green.greengram.user;

import com.green.greengram.user.model.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface UserService {

    int postSignUp(MultipartFile pic, SignUpPostReq p);

    SignInRes postSignIn(HttpServletResponse res, SignInPostReq p);

    UserInfoGetRes getUserInfo(UserInfoGetReq p);

    String patchProfilePic(UserProfilePatchReq p);
    int patchPassword(PatchPasswordReq p);



}

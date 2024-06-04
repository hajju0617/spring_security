package com.green.greengram.user;

import com.green.greengram.user.model.*;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    int postSignUp(MultipartFile pic, SignUpPostReq p);

    SignInRes postSignIn(SignInPostReq p);

    UserInfoGetRes getUserInfo(UserInfoGetReq p);

    String patchProfilePic(UserProfilePatchReq p);
    int patchPassword(PatchPasswordReq p);
}

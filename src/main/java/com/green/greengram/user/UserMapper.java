package com.green.greengram.user;

import com.green.greengram.user.model.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int postUser(SignUpPostReq p);

    User getUserById(String p);

    int patchPassword(PatchPasswordReq p);

    UserInfoGetRes selProfileUserInfo(UserInfoGetReq p);

    int updProfilePic(UserProfilePatchReq p);

}

package com.green.greengram.user;

import com.green.greengram.user.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    int postUser(SignUpPostReq p);

//    User getUserById(SignInPostReq p);
    List<UserInfo> getUserById(SignInPostReq p);


    UserInfoGetRes selProfileUserInfo(UserInfoGetReq p);

    int updProfilePic(UserProfilePatchReq p);

    List<User> selTest(long userId);

}

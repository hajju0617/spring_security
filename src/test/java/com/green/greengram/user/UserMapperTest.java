package com.green.greengram.user;

import com.green.greengram.user.model.User;
import com.green.greengram.user.model.UserInfoGetReq;
import com.green.greengram.user.model.UserInfoGetRes;
import com.green.greengram.user.model.UserProfilePatchReq;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


@MybatisTest
@ActiveProfiles("tdd")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserMapperTest {

    @Autowired
    private UserMapper mapper;
    @Test
    void getUserById() {
        User user1 = mapper.getUserById("사용자1");
        List<User> userList1 = mapper.selTest(user1.getUserId());
        User user1Comp = userList1.get(0);
        assertEquals(user1Comp, user1);

        User user3 = mapper.getUserById("사용자3");
        List<User> userList3 = mapper.selTest(user3.getUserId());
        User user3Comp = userList3.get(0);
        assertEquals(user3Comp, user3);

        User userNo = mapper.getUserById("fdsaㄴㄹㅇㄴㄹㅇg");
        assertNull(userNo, "없는 사용자 레코드가 넘어옴");
    }

    @Test
    void selProfileUserInfo() {
        UserInfoGetReq req1 = new UserInfoGetReq(2, 1);
        UserInfoGetRes res1 = mapper.selProfileUserInfo(req1);

        UserInfoGetRes expectedRes1 = new UserInfoGetRes();
        expectedRes1.setNm("홍길동");
        expectedRes1.setPic("363403f9-8d51-4a5c-a891-abaf8b317910.jpg");
        expectedRes1.setCreatedAt("2024-05-03 14:35:08");
        expectedRes1.setFeedCnt(1);
        expectedRes1.setFavCnt(0);
        expectedRes1.setFollowing(4);
        expectedRes1.setFollower(2);
        expectedRes1.setFollowState(3);

        assertEquals(expectedRes1, res1, "signedUser:2, profileUserId:1");

    }

    @Test
    void updProfilePicMe() {
        // 수정 되기 전 전체 리스트 가져옴
        List<User> beforeUserList = mapper.selTest(0);

        long modUserId = 1;
        String picName1 = "test.jpg";
        UserProfilePatchReq req1 = new UserProfilePatchReq();
        req1.setSignedUserId(modUserId);
        req1.setPicName(picName1);
        int affectedRows = mapper.updProfilePic(req1);

        assertEquals(1, affectedRows);

        List<User> userList1 = mapper.selTest(modUserId);
        User user1 = userList1.get(0);

        assertEquals(picName1, user1.getPic());

        //수정 된 후 전체 리스트 가져옴
        List<User> afterUserList = mapper.selTest(0);

        for(int i=0; i< beforeUserList.size(); i++) {
            User beforeUser = beforeUserList.get(i);
            if(beforeUser.getUserId() == modUserId) {
                assertNotEquals(beforeUser, afterUserList.get(i));
                continue;
            }
            assertEquals(beforeUser, afterUserList.get(i));
        }
    }

    @Test
    void updProfilePicNoUser() {
        List<User> beforeUserList = mapper.selTest(0);

        //없는 userId로 update 시도시 affectedRows 0이 넘어오는지 체크
        String picName1 = "test.jpg";
        UserProfilePatchReq req1 = new UserProfilePatchReq();
        req1.setSignedUserId(100);
        req1.setPicName(picName1);
        int affectedRows = mapper.updProfilePic(req1);

        assertEquals(0, affectedRows);

        List<User> afterUserList = mapper.selTest(0);

        for(int i=0; i< beforeUserList.size(); i++) {
            assertEquals(beforeUserList.get(i), afterUserList.get(i));
        }
    }

    @Test
    void updProfilePicYou() {

    }
}
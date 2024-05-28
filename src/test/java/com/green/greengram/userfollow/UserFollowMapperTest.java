package com.green.greengram.userfollow;

import com.green.greengram.userfollow.model.UserFollowEntity;
import com.green.greengram.userfollow.model.UserFollowReq;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("tdd")
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class UserFollowMapperTest {

    @Autowired
    private UserFollowMapper mapper;



    final int RECORD_COUNT = 12;
    @Test
    @DisplayName("유저 팔로우 insert 테스트")
    void insUserFollow() {
        UserFollowReq p1 = new UserFollowReq(0,0);
        List<UserFollowEntity> list1 = mapper.selUserFollowForTest(p1);

        UserFollowReq p2 = new UserFollowReq(4, 5);
        int affectedRows = mapper.insUserFollow(p2);
        Assertions.assertEquals(1, affectedRows);
//        assertEquals(1, affectedRows);        // static import 이므로 이렇게 쓸 수 도 있다
        System.out.println("result : " + affectedRows);

        List<UserFollowEntity> list2 = mapper.selUserFollowForTest(p1);
        int recordCountAfterFirstInsert = list2.size();
        assertEquals(1, recordCountAfterFirstInsert - list1.size(), "1. 실제 INSERT 되지 않음!");

        List<UserFollowEntity> list3 = mapper.selUserFollowForTest(p2);
        assertEquals(1, list3.size(), "p2 값이 제대로 insert 되지 않음");

        assertEquals(p2.getFromUserId(), list3.get(0).getFromUserId());
        assertEquals(p2.getToUserId(), list3.get(0).getToUserId());

        UserFollowReq p3 = new UserFollowReq(5, 1);
        int affectedRows2 = mapper.insUserFollow(p3);
        Assertions.assertEquals(1, affectedRows2);
        List<UserFollowEntity> list4 = mapper.selUserFollowForTest(p1);
        assertEquals(1, list4.size() - list2.size(), "2. 실제 insert 되지 않음");

        List<UserFollowEntity> list5 = mapper.selUserFollowForTest(p3);
        assertEquals(1, list5.size(), "p3값이 제대로 insert 되지 않음");
    }

    @Test
    @DisplayName("유저 팔로우 select 테스트")
    void selUserFollowForTest() {
        // 1. 전체 레코드 테스트
        UserFollowReq p1 = new UserFollowReq(0, 0);
        List<UserFollowEntity> list1 = mapper.selUserFollowForTest(p1);
        assertEquals(12, list1.size(), "레코드 수가 다르다.");

        UserFollowEntity record0 = list1.get(0);
        assertEquals(1, record0.getFromUserId(), "1. 0번 레코드 from_user_id 다름");
        assertEquals(2, record0.getToUserId(), "1. 0번 레코드 to_user_id 다름");

        assertEquals(new UserFollowEntity(1, 3, "2024-05-20 16:49:53"), list1.get(1)
                , "1. 1번 레코드 값이 다름");

        // 2. fromUserId = 1
        UserFollowReq p2 = new UserFollowReq(1, 0);
        List<UserFollowEntity> list2 = mapper.selUserFollowForTest(p2);
        assertEquals(4, list2.size(), "2. 레코스 수가 다르다");
        assertEquals(new UserFollowEntity(1, 2, "2024-05-20 16:49:53"), list2.get(0)
                , "2. 0번 레코드 값이 다름");
        assertEquals(new UserFollowEntity(1, 3, "2024-05-20 16:49:53"), list2.get(1)
                , "2. 1번 레코드 값이 다름");

        // 3. fromUserId = 300
        UserFollowReq p3 = new UserFollowReq(300, 0);
        List<UserFollowEntity> list3 = mapper.selUserFollowForTest(p3);
        assertEquals(0, list3.size(), "3. 레코드가 넘어오면 안 됨.");

        // 4. toUserId = 1
        UserFollowReq p4 = new UserFollowReq(0, 1);
        List<UserFollowEntity> list4 = mapper.selUserFollowForTest(p4);
        assertEquals(2, list4.size(), "4. 레코스 수가 다르다");
        assertEquals(new UserFollowEntity(2, 1, "2024-05-20 16:49:53"), list4.get(0)
                , "4. 0번 레코드 값이 다름");
        assertEquals(new UserFollowEntity(3, 1, "2024-05-20 16:49:53"), list4.get(1)
                , "4. 1번 레코드 값이 다름");
    }

    @Test
    void delUserFollow() {
    }
}
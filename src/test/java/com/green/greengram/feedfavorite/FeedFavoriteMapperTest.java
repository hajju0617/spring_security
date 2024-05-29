package com.green.greengram.feedfavorite;

import com.green.greengram.feedfavorite.model.FeedFavoriteEntity;
import com.green.greengram.feedfavorite.model.FeedFavoriteToggleReq;
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
class FeedFavoriteMapperTest {

    @Autowired
    private FeedFavoriteMapper mapper;
    @Test
    void insFeedFavorite() {
    }

    @Test
    void selFeedFavoriteForTest() {
        FeedFavoriteToggleReq entireParam = new FeedFavoriteToggleReq();
        List<FeedFavoriteEntity> entireList = mapper.selFeedFavoriteForTest(entireParam);

        assertEquals(10, entireList.size(), "전체 레코드 가져오는 부분");

        FeedFavoriteEntity req0 = new FeedFavoriteEntity();
        req0.setFeedId(5);
        req0.setUserId(1);
        req0.setCreatedAt("2024-05-20 12:57:04");
        assertEquals(req0, entireList.get(0), "0번 레코드");

        FeedFavoriteEntity res3 = new FeedFavoriteEntity();
        res3.setFeedId(8);
        res3.setUserId(4);
        res3.setCreatedAt("2024-05-20 12:57:03");
        assertEquals(res3, entireList.get(3), "3번 레코드");
    }


    @Test
    void delFeedFavorite() {
    }
}
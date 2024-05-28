package com.green.greengram.feedcomment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FeedCommentPostReq {
    @JsonIgnore private long feedCommentId;
    // pk값 반환 받고 싶지만 JsonIgnore 처리로 인해서 pk 값을 숨김

    // xml 파일에 데이터 3개 값을 주기 위해서
    private long feedId;
    private long userId;
    private String comment;
}

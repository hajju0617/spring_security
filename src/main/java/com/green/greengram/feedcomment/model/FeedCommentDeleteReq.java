package com.green.greengram.feedcomment.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.beans.ConstructorProperties;

@Getter
@EqualsAndHashCode
public class FeedCommentDeleteReq {
    private long feedCommentId;
    private long signedUserId;

    @ConstructorProperties({"feed_comment_id", "signed_user_id"})
    // feed_comment_id, signed_user_id : 프론트에서 보내주는 변수값? 을 long feedCommentId,long signedUserId 로 보내줌
    public FeedCommentDeleteReq(long feedCommentId,long signedUserId) {
        this.feedCommentId = feedCommentId;
        this.signedUserId = signedUserId;
        // setter를 안 쓸 경우 중간에 데이터값이 변조될 가능성이 없다. ---> 데이터의 일관성을 유지할 수 있다
    }
}

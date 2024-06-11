package com.green.greengram.feedfavorite.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.beans.ConstructorProperties;

@EqualsAndHashCode
@Getter
@ToString
public class FeedFavoriteToggleReq {

    private long userId;


    private long feedId;

    @ConstructorProperties({"feed_id", "user_id"})
    public FeedFavoriteToggleReq(long feedId, long userId) {
        this.feedId = feedId;
        this.userId = userId;
    }
}

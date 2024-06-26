package com.green.greengram.feedfavorite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.beans.ConstructorProperties;

@EqualsAndHashCode
@Getter
@ToString
public class FeedFavoriteToggleReq {

    @JsonIgnore
    private long userId;


    private long feedId;

    @ConstructorProperties({"feed_id"})
    public FeedFavoriteToggleReq(long feedId) {
        this.feedId = feedId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

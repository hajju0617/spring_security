package com.green.greengram.feedfavorite.model;

import lombok.Data;

@Data
public class FeedFavoriteToggleReq {
    private long userId;
    private long feedId;
}

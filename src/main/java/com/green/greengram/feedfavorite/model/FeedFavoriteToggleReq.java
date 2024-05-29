package com.green.greengram.feedfavorite.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FeedFavoriteToggleReq {
    private long userId;
    private long feedId;
}

package com.green.greengram.feed.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FeedPostReq {
    @JsonIgnore
    private long userId;

    private String contents;
    private String location;

    @JsonIgnore private long feedId;
}

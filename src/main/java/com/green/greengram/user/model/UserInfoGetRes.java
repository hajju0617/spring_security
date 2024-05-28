package com.green.greengram.user.model;

import lombok.Data;

@Data
public class UserInfoGetRes {
    private String nm;
    private String pic;
    private String createdAt;
    private int feedCnt;
    private int favCnt;
    private int following;
    private int follower;
    private int followState;
}

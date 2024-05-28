package com.green.greengram.userfollow.model;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode  //
@AllArgsConstructor
@ToString
public class UserFollowEntity {
    private long fromUserId;
    private long toUserId;
    private String createdAt;


}

package com.green.greengram.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class MyUserOAuth2Vo extends MyUser {       // Value Object
    private final String nm;
    private final String pic;

    public MyUserOAuth2Vo(long userId, String role, String nm, String pic) {
        super(userId, role);
        this.nm = nm;
        this.pic = pic;
    }
}

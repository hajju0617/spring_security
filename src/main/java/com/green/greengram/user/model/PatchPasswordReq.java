package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class PatchPasswordReq {
    private String uid;
    private String currentPw;
    private String newPw;

    @JsonIgnore private long userId;

}

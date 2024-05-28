package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserProfilePatchReq {
    private long signedUserId;
    private MultipartFile pic;

    @JsonIgnore
    private String picName;
}

package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengram.security.SignInProviderType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignInPostReq {
    @Schema(example = "abc", description = "유저 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;
    @Schema(example = "123", description = "유저 비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;

    @JsonIgnore
    private SignInProviderType providerType;



}

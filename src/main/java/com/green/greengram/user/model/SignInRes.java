package com.green.greengram.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignInRes {
    @Schema(description = "유저 pk")
    private long userId;
    @Schema(description = "유저 이름")
    private String nm;
    @Schema(description = "유저 프로필 사진")
    private String pic;

}

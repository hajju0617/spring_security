package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SignUpPostReq {
    @Schema(example = "abc", description = "아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;
    @Schema(example = "123", description = "비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;
    @Schema(example = "가나다", description = "이름", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nm;

    @JsonIgnore private String pic; // MultipartFile pic 로 받음.

    @JsonIgnore private long userId;
}

package com.green.greengram.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.green.greengram.security.SignInProviderType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Singular;

@Data
public class SignInPostReq {
                                                            // 값을 입력하지 않을 시 400 에러가 발생, controller 까지 오지도 않고 그 전에 미리 막아줌
    @NotBlank(message = "아이디를 확인해 주세요.")  // @NotBlank : 비어 있으면 안된다. (message : 문제가 생겼을 때 출력할 메세지)
    @Schema(example = "abc", description = "유저 아이디", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;

    @NotBlank(message = "비밀번호를 확인해 주세요.")
    @Size(min = 5, max = 20, message = "비밀번호는 5~20자로 구성해 주세요.")     // @Size 는 문자열             // @Min, @Max 는 숫자
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @Schema(example = "123", description = "유저 비밀번호", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;

    @JsonIgnore
    private String providerType;



}

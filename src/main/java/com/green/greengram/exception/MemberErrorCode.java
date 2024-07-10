package com.green.greengram.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다"),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다.");
    // 생성자를 호출하면서 enum을 만듦 (HttpStatus.BAD_REQUEST -> httpStatus, "유효하지 않은 토큰입니다." -> message)


    private final HttpStatus httpStatus;
    private final String message;
}

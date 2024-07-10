package com.green.greengram.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;


/*
Advice 라는 단어가 보이면 AOP 라고 인식하면 된다.
AOP(Aspect Oriented Programming) 란 관점지향 프로그래밍
Exception을 잡아낸다. (모두 or 개별 가능)
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)   // 우리가 커스텀한 예외가 발생 했을 경우 잡는다 (CustomException이 들어오면 얘가 잡는다.)
    public ResponseEntity<Object> handleException(CustomException e) {
        log.error("CustomException - handlerException : {}", e);
        return handleExceptionInternal(e.getErrorCode());
    }

    @Override   // Validation 예외가 발생했을 경우 잡는다
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(CommonErrorCode.INVALID_PARAMETER, ex);
    }

    @ExceptionHandler(Exception.class)  // 모든 Exception을 잡는다고 보면 됨  (CustomException 을 제외한)
    public ResponseEntity<Object> handleException(Exception e) {
        log.error("Exception - handlerException : {}", e);

        return handleExceptionInternal(CommonErrorCode.INTERNAL_SERVER_ERROR);
        // return handleExceptionInternal(null);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return handleExceptionInternal(errorCode, null);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode, BindException e) {
        return ResponseEntity.status(errorCode.getHttpStatus()).body(makeErrorResponse(errorCode, e));
    }

    private MyErrorResponse makeErrorResponse(ErrorCode errorCode, BindException e) {
        return MyErrorResponse.builder()
                .statusCode(errorCode.getHttpStatus())
                .resultMsg(errorCode.getMessage())
                .resultData(errorCode.name())   // errorCode.name() : enum의 이름
                .valids(e == null ? null : getValidationError(e))   // validation 에러 메세지를 정리하는 메서드
                .build();
    }

    private List<MyErrorResponse.ValidationError> getValidationError(BindException e) {
        // 반환타입을 MyErrorResponse.ValidationError 로 적은 이유 : 반환 타입이 ValidationError인데 ValidationError이 MyErrorResponse의 inner 클래스이므로
        List<MyErrorResponse.ValidationError> list = new ArrayList<>();
        List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();

        for(FieldError fieldError : fieldErrorList) {
            MyErrorResponse.ValidationError validError = MyErrorResponse.ValidationError.of(fieldError);
            list.add(validError);
        }
        return list;
    }
}

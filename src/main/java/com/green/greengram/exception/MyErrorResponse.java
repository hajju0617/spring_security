package com.green.greengram.exception;

import com.green.greengram.common.model.MyResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
@SuperBuilder   // 부모까지 빌더패턴 쓸 수 있게 해줌
public class MyErrorResponse extends MyResponse<String> {       // <String> 이유 : return 할때 항상 resultData 타입을 지정해줬는데 그걸 String으로 해서 상속 받겠다는 의미


    private final List<ValidationError> valids;     // Validation 에러가 발생시 메세지들을 리스트에 담아주는 담당

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {   // Validation 에러가 발생시 해당하는 에러 메세지를 표시할 때 객체 (5개중 3개가 에러 발생했다면 3개 에러 다 표시해줌)
                                            // inner class 는 static을 붙여주면 성능이 좋아진다.

        private final String field;         // validation 에러가 발생된 멤버필드명
        private final String message;       // validation 에러 메세지

        public static ValidationError of(final FieldError fieldError) { // ValidationError 객체 생성을 담당하는 메서드 (static 이므로 객체를 생성하지 않아도 호출 가능)
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}

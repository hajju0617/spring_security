package com.green.greengram.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration      // 스프링에게 설정파일이라고 알림
@OpenAPIDefinition(
        info = @Info(
                title = "그린그램"    // Swagger 에서 Servers 바로 위쪽 네이밍 변경
                , description = "Greengram with React"  // Swagger 에서 Servers 바로 위쪽 네이밍의 부제목 설정
                , version = "v2"    // Swagger 에서 Servers 바로 위쪽 네이밍에 지수형태로 표현하고 싶을때

                // 추후 인증처리도 작성
        )
)
public class SwaggerConfiguration {
}

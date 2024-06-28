package com.green.greengram.common;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

//@Configuration      // 스프링에게 설정파일이라고 알림
@OpenAPIDefinition(
        info = @Info(
                title = "그린그램"            // Swagger 에서 Servers 바로 위쪽 네이밍 변경
                , description = "Greengram with React"  // Swagger 에서 Servers 바로 위쪽 네이밍의 부제목 설정
                , version = "v3"    // Swagger 에서 Servers 바로 위쪽 네이밍에 지수형태로 표현하고 싶을때

                // 추후 인증처리도 작성
        ),
        security = @SecurityRequirement(name = "authorization") //EndPoint마다 자물쇠 아이콘 생성(로그인 가능)
)
@SecurityScheme(
        type = SecuritySchemeType.HTTP
        , name = "authorization"
        , in = SecuritySchemeIn.HEADER
        , bearerFormat = "JWT"
        , scheme = "Bearer"
)
public class SwaggerConfiguration {

}

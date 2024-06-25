package com.green.greengram.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.service.annotation.GetExchange;

@Getter
@ConfigurationProperties(prefix = "app") // applicationl.yaml 파일(46번 라인)의 app 을 뜻함
//ConfigurationProperties : yaml에 작성되어 있는 데이터를 객체화 시켜주는 에노테이션

public class AppProperties {
    private final Jwt jwt = new Jwt();

    @Getter
    @Setter
    public static class Jwt { // Jwt = applicationl.yaml 파일(47번 라인)의 jwt 을 뜻함
        // 멤버필드명은 application.yaml 의 app/jwt/* 속성명과 매칭
        // application.yaml 에서 '-'는 멤버필드에서 카멜케이스 기법과 매칭



        private String secret;                  // secret
        private String headerSchemaName;        // header-schema-name
        private String tokenType;               // token-type
        private long accessTokenExpiry;         // access-token-expiry
        private long refreshTokenExpiry;        // refresh-token-expiry
        private int refreshTokenCookieMaxAge;

        public void setRefreshTokenExpiry(long refreshTokenExpiry) {
            this.refreshTokenExpiry = refreshTokenExpiry;
            this.refreshTokenCookieMaxAge = (int)(refreshTokenExpiry * 0.001);      // yaml에서 시간 단위가 ms 라서 s로 변환 (ms = 1 / 1000초)
        }
    }
}

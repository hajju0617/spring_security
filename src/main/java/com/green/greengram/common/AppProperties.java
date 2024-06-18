package com.green.greengram.common;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.service.annotation.GetExchange;

@Getter
@ConfigurationProperties(prefix = "app") // applicationl.yaml 파일(46번 라인)의 app 을 뜻함 (
public class AppProperties {
    private final Jwt jwt = new Jwt();

    @Getter
    @Setter
    public static class Jwt { // Jwt = applicationl.yaml 파일(47번 라인)의 jwt 을 뜻함
        // 멤버필드명은 application.yaml 의 app/jwt/* 속성명과 매칭
        // application.yaml 에서 '-'는 멤버필드에서 카멜케이스 기법과 매칭



        private String secret;
        private String headerSchemaName;
        private String tokenType;
        private long accessTokenExpiry;
        private long refreshTokenExpiry;
        private int refreshTokenCookieMaxAge;

        public void setRefreshTokenExpiry(long refreshTokenExpiry) {
            this.refreshTokenExpiry = refreshTokenExpiry;
            this.refreshTokenCookieMaxAge = (int)(refreshTokenExpiry * 0.001); // yaml에서 시간 단위가 ms 라서 s로 변환 (ms = 1 / 1000초)
        }
    }
}

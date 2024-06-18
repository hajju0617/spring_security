package com.green.greengram.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.green.greengram.common.AppProperties;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;

@Slf4j
@Component  // 빈 등록 + 싱글톤
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final ObjectMapper om;
    private final AppProperties appProperties;
    private SecretKeySpec secretkeySpec;

    @PostConstruct  // 생성자 호출 이후에 한번 실행하는 메서드
    public void init() {
        this.secretkeySpec = new SecretKeySpec(appProperties.getJwt().getSecret().getBytes()
                                             , SignatureAlgorithm.HS256.getJcaName());
    }

    public String generateAccessToken() {
        return null;
    }
}

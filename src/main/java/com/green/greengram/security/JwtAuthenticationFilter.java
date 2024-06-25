package com.green.greengram.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component  // 빈 등록 ( 클래스 단위 )
public class JwtAuthenticationFilter extends OncePerRequestFilter {     // OncePerRequestFilter : 한 페이지에서 여러번 요청이 와도 딱 한번만 필터
    private final JwtTokenProviderV2 jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);  // header에 authorization 키에 저장 되어 있는 값을 리턴 (있으면 문자열(JWT), 없으면 null)
                                                                // JWT 값이 있으면 로그인 상태, null -> 비로그인 상태(로그아웃 상태)
        log.info("JwtAuthenticationFilter-Token : {}", token);

        if(token != null && jwtTokenProvider.isValidateToken(token)) {  // 토큰이 정상적으로 저장되어 있고 만료가 되지 않았다면
            Authentication auth = jwtTokenProvider.getAuthentication(token);   // SecurityContextHolder의 Context의 담기 위한 Authentication 객체 생성

            if(auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);     // Authentication 객체 주소값을 담으면 인증 되었다고 인식
            }
        }
        filterChain.doFilter(request, response);
    }
}

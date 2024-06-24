package com.green.greengram.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {     // OncePerRequestFilter : 한 페이지에서 여러번 요청이 와도 딱 한번만 필터
    private final JwtTokenProviderV2 jwtTokenProvider;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);
        log.info("JwtAuthenticationFilter-Token : {}", token);

        if(token != null && jwtTokenProvider.isValidateToken(token)) {  // 토큰이 정상적으로 저장되어 있고 만료가 되지 않았다면
            Authentication auth = jwtTokenProvider.getAuthentication(token);

            if(auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request, response);
    }
}

package com.green.greengram.security.jwt;

import com.green.greengram.exception.CustomException;
import com.green.greengram.exception.MemberErrorCode;
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

/*
JwtAuthenticationFilter doFilterInternal
-> JwtTokenProviderV2 resolveToken
-> JwtTokenProviderV2 isValidateToken
-> if ( 토큰 있고 && 토큰 만료시간이 안 지나고 변형이 없었다면) => jwtTokenProvider.getAuthentication(token) 호출
-> SecurityContextHolder.getContext().setAuthentication();
-> filterChain.doFilter
-> Controller method 호출
 */

/*
        HttpServletRequest (이하 Req), HttpServletResponse (이하 Res), Client (서버에게 요청을 보내는 EndUser)
        Req : 요청에 관련된 모든 정보가 담겨져 있는 객체 ( Client의 IP주소, 사용하는 브라우저 엔진, OS, URL, 쿼리스트링, Body, Header 어떤 데이터가 담겨져 있는 지 등등)
        Res : 서버가 응답을 할 때 사용할 객체
*/

@Slf4j
@RequiredArgsConstructor
@Component  // 빈 등록 ( 클래스 단위 )
public class JwtAuthenticationFilter extends OncePerRequestFilter {     // OncePerRequestFilter : 한 페이지에서 여러번 요청이 와도 딱 한번만 필터
    private final JwtTokenProviderV2 jwtTokenProvider;


    @Override                   // 요청이 들어오면 HttpServletRequest, HttpServletResponse 두개가 같이 생긴다. (request 요청 값, response : 반환 값) -> 모든 요청값이 request에 담기고, response는 반환값으로 쓰려고
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);  // header의 authorization 키에 저장 되어 있는 값을 리턴 (있으면 문자열(JWT), 없으면 null)
                                                                // JWT 값이 있으면 로그인 상태, null 이면 비로그인 상태(로그아웃 상태)
                                                                // img, css, js, favicon 등을 요청할 때와 프론트가 header에 accessToken을 담지 않았을때(프론트의 실수 or 비로그인) accessToken이 없음
        log.info("JwtAuthenticationFilter-Token : {}", token);

        if(token != null && jwtTokenProvider.isValidateToken(token)) {  // 토큰이 정상적으로 저장되어 있고 && 만료가 되지 않았다면

            Authentication auth = jwtTokenProvider.getAuthentication(token);   // SecurityContextHolder의 Context의 담기 위한 Authentication 객체 생성
                                                                               // token으로 부터 myUser 얻고 -> MyUserDetails에 담고 -> UsernamePasswordAuthenticationToken 에 담아서 리턴
                                                                               // UsernamePasswordAuthenticationToken이 Authentication의 자식(자손) 클래스

            if(auth != null) {
                SecurityContextHolder.getContext().setAuthentication(auth);     // SecurityContextHolder.getContext()에 Authentication 객체 주소값을 담으면 인증(로그인) 되었다고 인식 ==> 로그인 처리
                                                // setAuthentication(auth) : 값을 집어 넣음
            }
        }
        filterChain.doFilter(request, response);    // 다음 필터로 넘긴다. 만약 로그인이 필요한 엔드 포인트(url)인데 로그인이 되어 있지 않으면
                                                    // JwtAuthenticationEntryPoint에 의해서 401에러를 응답
                                                    // 만약 권한이 필요한 엔드포인트(url)인데 권한이 없으면 JwtAuthenticationEntryPoint에 의해서 403에러를 응답
                                                    // 엔드 포인트 세팅은 SecurityConfiguration의 securityFilterChain 메서드에서 한다 where? -> (64번 행) authorizeHttpRequests(auth -> auth.requestMatchers( 하단 부분
    }
}

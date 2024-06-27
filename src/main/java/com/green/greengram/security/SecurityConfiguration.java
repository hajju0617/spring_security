package com.green.greengram.security;


import com.green.greengram.security.jwt.JwtAuthenticationAccessDeniedHandler;
import com.green.greengram.security.jwt.JwtAuthenticationEntryPoint;
import com.green.greengram.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/*
JSP
요청시 저장 공간
PageContext : JSP 내부에서만 사용
Request : controller -> service -> JSP 전송 (Request는 일회용)
Session
Application
 */

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;  // @Component로 빈등록을 하였기 때문에 DI가 된다.

    /*
     메서드 빈 등록으로 주로 쓰는 케이스는 (현재 기준으로 설명하면)
     Security와 관련된 빈 등록을 여러개 하고 싶을 때 메서드 형식으로 빈 등록하면 한 곳에 모을 수가 있으니 좋다.
     메서드 빈 등록으로 하지 않으면 각각 클래스로 만들어야 한다.
     */

    @Bean   // 메서드 타입의 빈 등록 ( 파라미터, 리턴 타입이 중요), 파라미터는 빈 등록 할 때 필요한 객체
    // @Bean(빈등록 : 스프링 컨테이너에 객체화를 대리로 맡기는 개념) 어노테이션이 붙은 메서드가 있으면 스프링 컨테이너가 무조건 호출. (빈 등록이 이미 되어 있는 매개변수에 DI 해줌).
    // @Configuration 을 붙이는 순간 이 메서드(securityFilterChain)가 싱글톤이 된다.
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception { //HttpSecurity httpSecurity 은 라이브러리를 등록할 시점에 이미 빈등록 되어 있음
        // 파라미터 없이 내가 직접 new 객체화해서 리턴으로 빈 등록 가능하다.


        return httpSecurity.sessionManagement(session ->        // -> : arrow function
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // 이 메서드가 리턴한 객체를 빈 등록 해준다. (리턴 타입이 SecurityFilterChain 이지만 상속관계에서 자식 타입이 리턴 될 수도 있다)
        // 위 람다식을 풀어쓰면 아래와 같다. 람다식은 짧게 함축적으로 적을 수 있는 기법

//        return httpSecurity.sessionManagement(new Customizer<SessionManagementConfigurer<HttpSecurity>>() {
//            @Override
//            public void customize(SessionManagementConfigurer<HttpSecurity> session) {
//                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//            }
//        }).build();
        )       .httpBasic(http -> http.disable())  //(SSR 서버사이드 렌더링을 하지 않는다. 즉, html화면을 백엔드가 만들지 않는다)
                // 백엔드에서 화면을 만들지 않더라도 위 세팅을 끄지 않아도 괜찮다. 사용하지 않는 걸 끔으로써 리소스를 확보 하기 위해서 사용하는 개념
                // 정리하면 Security에서 제공해주는 로그인 화면 사용하지 않겠다.
                .formLogin(form -> form.disable())  // form 로그인 방식을 사용하지 않음을 세팅 (Security 가 제공해주는 로그인 화면 사용하지 않음) (리소스 확보)
                .csrf(csrf -> csrf.disable()) // CSRF(Cross-Site Request Forgery) (CORS랑 많이 헷갈려 하는 개념)
                                              // CSRF(Cross-Site Request Forgery) - 사이트 간 요청 위조 ( -> 끄는 이유 : 우리는 토큰 방식으로 로그인 함)
                                              // 사용자가 자신의 의지와는 무관하게 공격자가 의도한 행위(수정, 삭제, 등록 등)를 특정 웹사이트에 요청하게 하는 공격을 말함
                                              // CSRF 공격이란 정상적인 사용자가 의도하지 않았지만, 자신도 모르게 서버를 공격하게 되는 경우임. 공격자가 만든 악성 페이지를 통해 사용자는 자신도 모르게 공격을 수행 함.
                                              // CSRF 공격은 로그인 방식이 세션일때
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/user/sign-up"        // 회원 가입, 로그인 인증이 안 되어 있더라도
                        , "/api/user/sign-in"                           // 사용 가능 하게 세팅
                        , "/api/user/access-token"

                        , "/swagger"            // swagger 사용할 수 있게 세팅
                        , "/swagger-ui/**"      // ** 의미 : 뒤쪽에 어떤 값이 들어와도 상관없다는 의미
                        , "/v3/api/docs/**"

                        , "/pic/**"             // /pic/aaaa.jpg    /pic/aabb/abab.jpg   /pic/abcd/qwer/zxcv/ddsa.jpg
                                                  // /pic/aaa.jpg   *가 하나면 이것만 가능
                        , "/fimg/**"

                        , "/"                   // localhost/8080 보이게 세팅
                        , "/index.html"         // 프론트 화면이 보일 수 있게 세팅
                        , "/css/**"             // css
                        , "/js/**"              // 자바스크립트
                        , "/static/**"

                        // 프론트에서 사용하는 라우터 주소
                        ,"/sign-in"
                        ,"/sign-up"
                        ,"/profile/*"
                        ,"/feed"
                        ).permitAll()
                                .anyRequest().authenticated()   // 위쪽 주소를 제외하고는 로그인이 되어 있어야만 허용


                ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // UsernamePasswordAuthenticationFilter.class 이전에 jwtAuthenticationFilter 해당 필터를 위치해준다는 뜻
                .exceptionHandling(exception -> exception.authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                                                         .accessDeniedHandler(new JwtAuthenticationAccessDeniedHandler()))
                .build();




                /*
                //만약, permitAll메소드가 void였다면 아래와 같이 작성을 해야함      // 람다식에서 중괄호를 안 쓸 수 있는 조건 : 안에 1문장으로 작성 돼야 함 (문장 끝 세미콜론 없어도 됨)
                .authorizeHttpRequests(auth -> {                             // // 람다식에서 메소드 내부 코드를 감싸는 중괄호는 실행문이 한 문장일 때에는 생략 가능 (+ 한 문장일 경우 세미콜론( ; )도 생략 가능
                    //
                    auth.requestMatchers("/api/user/sign-up","/api/user/sign-in").permitAll();      // // permitAll() 리턴 타입은 나 자신의 주소값
                    auth.requestMatchers("/api/ddd").authenticated();
                })
                //permitAll 메소드가 자기 자신의 주소값을 리턴한다면 체이닝 기법 가능
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/user/sign-up","/api/user/sign-in").permitAll()
                        .requestMatchers("/api/ddd").authenticated();
                })
                */
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}




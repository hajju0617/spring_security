package com.green.greengram.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/*
로그인한 사용자 정보를 가져오는 객체를 만듦
How? -> Security Context Holder -> Context -> Authentication 에서 getPrincipal을 호출하여
우리가 전에 넣었던 정보(MyUserDetails)를 얻어와서 처리한다
Authentication이 해당 위치에 저장이 되어 있어야만 스프링 시큐리티가 로그인으로 인지한다.
 */


@Component
public class AuthenticationFacade {
    /*
        SecurityContext : 현재 애플리케이션의 보안 컨텍스트를 담고 있는 객체이다. 이 컨텍스트는 보통 현재 로그인한 사용자와 관련된 정보를 담고 있습니다.

        Authentication : 인증된 사용자의 정보를 나타내는 객체이다. 이 객체는 사용자가 성공적으로 로그인한 후 생성되며, 보통 SecurityContext에 저장된다.

        Principal : 인증된 사용자를 나타내는 객체이다. Spring Security에서는 보통 UserDetails 인터페이스를 구현한 객체가 principal로 사용된다.
                    이 객체는 사용자 이름, 암호, 권한 등 사용자의 주요 정보를 포함하고 있음
        ------------------------------------------------------------------------------------------

        SecurityContextHolder.getContext() : 현재 스레드의 SecurityContext를 가져옴. 이 컨텍스트는 현재 인증된 사용자와 관련된 정보를 포함하고 있음.

        getAuthentication() : SecurityContext에서 Authentication 객체를 가져옴. 이 객체는 현재 인증된 사용자의 정보를 담고 있음.

        getPrincipal() : Authentication 객체에서 principal(사용자 정보를 담고 있는 객체)을 가져온다.

        (MyUserDetails) : principal 객체를 MyUserDetails 타입으로 캐스팅함. 이는 principal 객체가 MyUserDetails 타입임을 보장하기 위해 필요.
     */
    public MyUser getLoginUser() {  // getLoginUser() : 현재 로그인한 사용자의 MyUser 객체를 반환
        MyUserDetails myUserDetails = (MyUserDetails)SecurityContextHolder.getContext()
                                                                          .getAuthentication()  // UsernamePasswordAuthenticationToken 객체 주소값 리턴
                                                                          .getPrincipal();
        // JwtAuthenticationFilter에서는 SecurityContextHolder.getContext().setAuthentication(auth)
        // SecurityContextHolder.getContext()에 Authentication 객체 주소값을 담으면 인증(로그인) 되었다고 인식 ==> 로그인 처리

        // AuthenticationFacade 에서는 SecurityContextHolder.getContext()
        //                                                 .getAuthentication()  // UsernamePasswordAuthenticationToken 객체 주소값 리턴
        //                                                 .getPrincipal();

        return myUserDetails == null ? null : myUserDetails.getMyUser();
        // 100% null 일수가 없는데 일단은 처리 해줬다.

        /*
         SecurityContext에서 Authentication 객체를 가져와서, Principal을 MyUserDetails 타입으로 캐스팅하고
         MyUserDetails 객체에서 MyUser 객체를 가져와 반환
         */
    }

    public long getLoginUserId() {  // getLoginUserId() : 현재 로그인한 사용자의 ID를 반환 (PK값).
        MyUser myUser = getLoginUser();
        return myUser == null ? 0 : myUser.getUserId();
    }

}

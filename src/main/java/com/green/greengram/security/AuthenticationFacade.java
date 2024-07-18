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
    public MyUser getLoginUser() {
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
    }

    public long getLoginUserId() {
        MyUser myUser = getLoginUser();
        return myUser == null ? 0 : myUser.getUserId();
    }

}

package com.green.greengram.security.oauth2.userinfo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public abstract class OAuth2UserInfo {
                   // <key 타입, value 타입>
    protected final Map<String, Object> attributes;
    // Social 플랫폼에서 받아온 Data(JSON)을 HashMap 으로 컨버팅하여 내가 직접 DI 해준다
    // OAuth2UserInfo 클래스의 attributes 필드는 소셜 플랫폼에서 받아온 데이터를 저장하는 데 사용 된다.
    // 이 데이터는 일반적으로 JSON 형식으로 제공되고 이를 HashMap 으로 변환하여 attributes 필드에 주입한다.

    public abstract String getId(); // 유니크값 리턴
    public abstract String getName();   // 이름 리턴
    public abstract String getEmail();  // 이메일 리턴
    public abstract String getProfilePicUrl();  // 프로필 사진
}

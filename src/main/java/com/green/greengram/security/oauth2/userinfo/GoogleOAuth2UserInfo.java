package com.green.greengram.security.oauth2.userinfo;


import java.util.Map;

// 부모가 기본생성자가 아닌 생성자(오버라이딩 된 생성자)만 가지고 있는 경우는 lombok 에노테이션으로 처리 불가 -> @RequiredArgsConstructor 사용해도 안됨.
// 그래서 직접 생성자 작성 해야 함.
public class GoogleOAuth2UserInfo extends OAuth2UserInfo {
    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {   // 직접 작성한 생성자
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");  // 반환값이 String 이므로 (String) 형변환
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getProfilePicUrl() {
        return (String) attributes.get("profilePicUrl");
    }
}

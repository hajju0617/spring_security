package com.green.greengram.security.oauth2.userinfo;

import com.green.greengram.security.SignInProviderType;
import org.springframework.stereotype.Component;

import java.util.Map;


//클래스 이름에 Factory가 들어가면 객체를 생성하는 역할
// 구글, 카카오, 네이버에서 받은 JSON Data -> HashMap -> 규격화 된 객체로 변환 (데이터 가져오는 부분을 통일 시키기 위해서) (규격화 된 객체: GoogleOAuth2UserInfo, KakaoOAuth2UserInfo, NaverOAuth2UserInfo

@Component
public class OAuth2UserInfoFactory {
    public OAuth2UserInfo getOAuth2UserInfo(SignInProviderType signInProviderType, Map<String, Object> attributes) {
        return switch (signInProviderType) {
            case GOOGLE -> new GoogleOAuth2UserInfo(attributes);            // case "GOOGLE" : SignInProviderType의 GOOGLE
            case KAKAO -> new KakaoOAuth2UserInfo(attributes);              // case "KAKAO" : SignInProviderType의 KAKAO
            case NAVER -> new NaverOAuth2UserInfo(attributes);              // case "NAVER" : SignInProviderType의 NAVER
            default -> throw new RuntimeException("제공하지 않는 로그인 방식임.");
        };
    }


}

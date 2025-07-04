package SANTA.backend.global.security.userinfoservice;

import org.apache.tomcat.websocket.AuthenticationException;

import java.util.Map;

public class OAuth2UserInfoFactory {
    private OAuth2UserInfoFactory() {
        throw new IllegalStateException("OAuth2UserInfoFactory의 인스턴스는 생성할 수 없습니다.");
    }

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) throws AuthenticationException {
        if (registrationId.equalsIgnoreCase("google")) {
            return new GoogleUserInfo(attributes);
        }
        throw new AuthenticationException(registrationId + " 로그인은 지원하지 않습니다.");
    }
}

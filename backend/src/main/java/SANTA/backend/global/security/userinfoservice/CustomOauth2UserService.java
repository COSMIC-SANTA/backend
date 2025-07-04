package SANTA.backend.global.security.userinfoservice;

import SANTA.backend.core.domain.User;
import SANTA.backend.core.repository.UserRepository;
import SANTA.backend.core.service.UserService;
import SANTA.backend.global.security.userinfo.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOauth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        try{
            OAuth2UserInfo oAuth2UserInfo=distinguish(userRequest);
            User user=userService.findById(oAuth2UserInfo.getId());
            return new CustomUserDetails(user);
        }
        catch ( IllegalAccessException e){
            throw new RuntimeException(e);
        }
    }

    private OAuth2UserInfo distinguish(OAuth2UserRequest userRequest)throws IllegalAccessException{
        String clientId=userRequest.getClientRegistration().getClientId();
        if(clientId.equals("google")){
            return new GoogleUserInfo(userRequest.getAdditionalParameters());

        }
        throw new IllegalAccessException("구글 로그인만 지원합니다.");
    }
}

package SANTA.backend.global.security.oauth.handler;

import SANTA.backend.global.jwt.JWTUtil;
import SANTA.backend.global.security.util.CookieUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

import static SANTA.backend.global.security.oauth.handler.HttpCookieOAuth2Auth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String targetUrl=determineTargetUrl(request,response,authentication);
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
        getRedirectStrategy().sendRedirect(request,response,targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,Authentication authentication) {
        String redirectUri = "http://localhost:8080/abc";
        //String redirectUri=CookieUtil.getCookie(request,REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue).get();

        String token=jwtUtil.createJwt(authentication);

        return UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam("token",token)
                .build().toUriString();
    }
}

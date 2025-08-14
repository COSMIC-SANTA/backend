package SANTA.backend.global.oauth.handler;

import SANTA.backend.global.jwt.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String targetUrl=determineTargetUrl(request,response,authentication);
        log.info("🔁 리다이렉트 대상 URL = {}", targetUrl);
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

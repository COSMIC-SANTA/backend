package SANTA.backend.core.auth.oauth.handler;

import SANTA.backend.core.auth.util.CookieUtil;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;

import static SANTA.backend.core.auth.oauth.handler.HttpCookieOAuth2Auth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
@RequiredArgsConstructor
public class FailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private final HttpCookieOAuth2Auth2AuthorizationRequestRepository httpCookieOAuth2Auth2AuthorizationRequestRepository;


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, java.io.IOException {
        String targetUrl = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue)
                .orElse(("/"));

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("error", URLEncoder.encode(exception.getMessage(), "UTF-8"))
                .build().toUriString();

        httpCookieOAuth2Auth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}

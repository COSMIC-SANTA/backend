package SANTA.backend.global.oauth.handler;

import SANTA.backend.core.auth.util.CookieUtil;
import SANTA.backend.global.common.AppProperties;
import SANTA.backend.global.exception.ErrorCode;
import SANTA.backend.global.exception.type.CustomException;
import SANTA.backend.global.jwt.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static SANTA.backend.global.oauth.handler.HttpCookieOAuth2Auth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    private final HttpCookieOAuth2Auth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    private final AppProperties appProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String targetUrl = determineTargetUrl(request, response, authentication);
        log.info("🔁 리다이렉트 대상 URL = {}", targetUrl);
        clearAuthenticationAttributes(request,response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_REDIRECT_URI);
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());


        String token = jwtUtil.createJwt(authentication);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        String authorizedRedirectUris = appProperties.getOAuth2().getAuthorizedRedirectUris();
        URI authorizedURI = URI.create(authorizedRedirectUris);
        return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost()) && authorizedURI.getPort() == clientRedirectUri.getPort();
    }
}

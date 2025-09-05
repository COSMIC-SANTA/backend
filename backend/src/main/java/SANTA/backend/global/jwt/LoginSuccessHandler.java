package SANTA.backend.global.jwt;

import SANTA.backend.core.auth.service.CustomUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JWTUtil tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws java.io.IOException, ServletException {
        String jwt = tokenProvider.createJwt(authentication);

        // ✅ jakarta.servlet.http.Cookie 사용
        Cookie cookie = new Cookie("accessToken", jwt);
        cookie.setHttpOnly(true); // JS에서 접근 못하게
        cookie.setSecure(true);   // HTTPS 환경에서만 사용
        cookie.setPath("/");      // 모든 경로에 대해 쿠키 전송
        cookie.setMaxAge(60*60*10000);
	cookie.setSecure(false);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("id", userDetails.getUserId());
        responseBody.put("nickname", userDetails.getNickname());
        responseBody.put("accessToken", jwt);

        response.addCookie(cookie); // ✅ jakarta.servlet.http.Cookie 를 사용해야 오류 없음

        response.setStatus(HttpStatus.OK.value());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        new ObjectMapper().writeValue(response.getWriter(), responseBody);
        response.getWriter().flush();
    }
}


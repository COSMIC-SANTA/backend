package SANTA.backend.global.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

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
        cookie.setMaxAge(60*60*100);
        cookie.setSecure(false);        

        response.addCookie(cookie); // ✅ jakarta.servlet.http.Cookie 를 사용해야 오류 없음

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"Login successful, token is set in cookie\"}" + "accessToken" + jwt);
    }
}

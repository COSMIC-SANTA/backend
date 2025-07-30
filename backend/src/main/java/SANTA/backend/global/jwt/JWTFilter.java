package SANTA.backend.global.jwt;

import SANTA.backend.core.user.domain.User;
import SANTA.backend.core.auth.service.CustomUserDetails;
import SANTA.backend.core.user.application.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {
    //요청에 대해 한번만 동작하는 onceperrequestFilter상속
    private final JWTUtil jwtUtil;
    private final UserService userService;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //검증 확인 하는 부분
        //request에서 Authorization 헤더를 찾음
        String authorization= request.getHeader("Authorization");
        //Authorization 헤더 검증 (적절한지, 접두가 알맞은지)
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        String token = authorization.split(" ")[1];

        //토큰 복호화로 사용자 인증
        Claims claims = jwtUtil.parseClaims(token);

        if (Boolean.TRUE.equals(redisTemplate.hasKey("blacklist:" + token))) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(claims)) {
            filterChain.doFilter(request, response);
            return;
        }

        //토큰에서 username과 role 획득
        Long id=jwtUtil.getUserId(claims);
        String username = jwtUtil.getUsername(claims);

        User user=userService.findById(id);


        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = CustomUserDetails.create(user);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //Auth토큰을 security~HOlder에 넣음. 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

}

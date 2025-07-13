package SANTA.backend.global.jwt;

import SANTA.backend.core.user.domain.User;
import SANTA.backend.core.auth.service.CustomUserDetails;
import SANTA.backend.core.user.application.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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
        //Bearer 부분 제거 후 순수 토큰만 획득.
        //2번쨰 칸인 1번 인덱스를 받음(토큰 구조[string형]- Bearer asdfgqweaoiasdjfja;jtoje)
        String token = authorization.split(" ")[1];

        //토큰 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {

            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }

        //토큰에서 username과 role 획득
        String id=jwtUtil.getUserId(token);
        String username = jwtUtil.getUsername(token);

        User user=userService.findById(Long.valueOf(id));


        //UserDetails에 회원 정보 객체 담기
        CustomUserDetails customUserDetails = CustomUserDetails.create(user);

        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
        //Auth토큰을 security~HOlder에 넣음. 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }

}

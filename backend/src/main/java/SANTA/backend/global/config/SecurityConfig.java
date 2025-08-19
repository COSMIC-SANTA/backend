package SANTA.backend.global.config;

import SANTA.backend.core.user.domain.Role;
import SANTA.backend.global.jwt.JWTFilter;
import SANTA.backend.global.jwt.JWTUtil;
import SANTA.backend.global.jwt.LoginFilter;
import SANTA.backend.global.jwt.LoginSuccessHandler;
import SANTA.backend.global.oauth.handler.FailureHandler;
import SANTA.backend.global.oauth.handler.HttpCookieOAuth2Auth2AuthorizationRequestRepository;
import SANTA.backend.global.oauth.handler.SuccessHandler;
import SANTA.backend.core.auth.service.CustomOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SuccessHandler successHandler;
    private final FailureHandler failureHandler;
    private final JWTFilter jwtFilter;
    private final HttpCookieOAuth2Auth2AuthorizationRequestRepository httpCookieOAuth2Auth2AuthorizationRequestRepository;
    private final CustomOauth2UserService customOauth2UserService;
    private final LoginSuccessHandler loginSuccessHandler;

    private static final String ROLE_ADMIN = "ADMIN";
    private static final String ROLE_USER = "USER";

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public LoginFilter loginFilter(AuthenticationManager authenticationManager) {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/api/auth/login");
        loginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        loginFilter.setAuthenticationManager(authenticationManager);
        return loginFilter;
    }

    /** ✅ 전역 CORS 설정 (패턴 + OPTIONS 포함) */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cfg = new CorsConfiguration();

        // 개발 단계: 정확한 오리진(8081) 허용. 필요하면 패턴/여러 항목 추가
        cfg.setAllowedOriginPatterns(List.of(
                "http://localhost:8081",
                "http://127.0.0.1:8081",
                "http://[::1]:8081"
        ));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","PATCH","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setExposedHeaders(List.of("*"));

        // withCredentials(쿠키/세션) 안 쓰면 false 권장. 쓰면 true + 정확한 Origin만 허용해야 함.
        cfg.setAllowCredentials(false);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cfg);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .formLogin(f -> f.disable())
                .httpBasic(b -> b.disable())
                .cors(Customizer.withDefaults())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/api/auth/sign-out")
                        .hasAnyRole(ROLE_USER,ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/form" )
                        .hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/main/banner")
                        .hasAnyRole(ROLE_USER, ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/main/banner/click","/api/main/weather")
                        .hasAnyRole(ROLE_USER, ROLE_ADMIN)
                        .requestMatchers( "/api/main/saveMountainsFromApi")
                        .hasRole(ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/mountains/**")
                        .hasAnyRole(ROLE_USER,ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/mountains/**")
                        .hasAnyRole(ROLE_USER,ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/plan/**")
                        .hasAnyRole(ROLE_USER,ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/plan/**")
                        .hasAnyRole(ROLE_USER,ROLE_ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/community/**")
                        .hasAnyRole(ROLE_USER,ROLE_ADMIN)
                        .requestMatchers(HttpMethod.POST, "/api/community/**")
                        .hasAnyRole(ROLE_USER,ROLE_ADMIN)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().permitAll()
                )

                .oauth2Login(oauth -> oauth
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                        .authorizationEndpoint(aep -> aep
                                .baseUri("/api/oauth2/authorize")
                                .authorizationRequestRepository(httpCookieOAuth2Auth2AuthorizationRequestRepository)
                        )
                        .redirectionEndpoint(redirection -> redirection.baseUri("/api/oauth2/callback/*"))
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOauth2UserService))
                );

        http.addFilterAfter(jwtFilter, CorsFilter.class);

        http.addFilterAt(loginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

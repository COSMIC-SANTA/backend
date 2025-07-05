package SANTA.backend.global.config;

import SANTA.backend.global.jwt.JWTFilter;
import SANTA.backend.global.jwt.JWTUtil;
import SANTA.backend.global.jwt.LoginFilter;
import SANTA.backend.global.jwt.LoginSuccessHandler;
import SANTA.backend.global.security.oauth.handler.FailureHandler;
import SANTA.backend.global.security.oauth.handler.HttpCookieOAuth2Auth2AuthorizationRequestRepository;
import SANTA.backend.global.security.oauth.handler.SuccessHandler;
import SANTA.backend.global.security.userinfoservice.CustomOauth2UserService;
import SANTA.backend.global.security.userinfoservice.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    //JWTUtil
    private final SuccessHandler successHandler;

    private final FailureHandler failureHandler;

    private final JWTFilter jwtFilter;

    private final JWTUtil jwtUtil;

    private final HttpCookieOAuth2Auth2AuthorizationRequestRepository httpCookieOAuth2Auth2AuthorizationRequestRepository;

    private final CustomOauth2UserService customOauth2UserService;

    private final LoginSuccessHandler loginSuccessHandler;


    @Bean //
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public LoginFilter loginFilter(AuthenticationManager authenticationManager) {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setFilterProcessesUrl("/login");
        loginFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        loginFilter.setAuthenticationManager(authenticationManager);
        return loginFilter;
    }

    @Bean
    public CorsConfigurationSource corsConfiguration() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource corsConfigurationSource=new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**",configuration);

        return corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception{
        //csrf disable : csrf 공격 방어
        //Form 로그인 방식 disable
        //http basic 인증방식 disable
        //경로별 인가 작업, OAuth2
        //세션 설정-stateless 상태로 설정하는 것이 중요
        http
                .csrf((auth)->auth.disable())
                .formLogin((auth)->auth.disable())
                .httpBasic((auth)->auth.disable())
                .cors(c->c.configurationSource(corsConfiguration()))
                .sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/api/auth/sign-up","/abc").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth->oauth
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                        .authorizationEndpoint(aep -> aep
                                .baseUri("/api/oauth2/authorize")
                                .authorizationRequestRepository(httpCookieOAuth2Auth2AuthorizationRequestRepository)
                        )
                        .redirectionEndpoint(redirectionEndpointConfig -> redirectionEndpointConfig
                                .baseUri("/api/oauth2/callback/*")
                        )
                        .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig
                                .userService(customOauth2UserService))
                );

        //JWT필터 등록
        http
                .addFilterBefore(jwtFilter,LoginFilter.class);
        //필터 추가 LoginFilter()는 인자를 받음 (AuthenticationManager() 메소드에 authenticationConfiguration 객체를 넣어야 함) 따라서 등록 필요
        http
                .addFilterAt(loginFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class);


        return  http.build();
    }
}

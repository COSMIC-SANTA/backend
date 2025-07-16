package SANTA.backend.global.jwt;

import SANTA.backend.core.auth.service.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    private SecretKey secretKey;

    public JWTUtil(JWTProperties jwtProperties) {
        String secret = jwtProperties.getSecret();
        this.secretKey = new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    //token의 특정 요소들을 검증하는 부분
    public String getUsername(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("username", String.class);
    }

    public Long getUserId(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("id", Long.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }
    //Claims 파싱 및 유효성 검증 매서드 추가
    public Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token expired");
        } catch (Exception e) {
            throw new RuntimeException("Invalid JWT token");
        }
    }
    //필수 Claim이 있는지 검증하는 매서드 추가
    public void validateRequiredClaims(Claims claims) {
        if (claims.get("username") == null || claims.get("id") == null) {
            throw new RuntimeException("Missing required claims");
        }
    }

    //token 생성
    public String createJwt(String username, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) //발행 시간
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) //소멸될 시간 설정
                .signWith(secretKey)
                .compact();
    }

    public String createJwt(Authentication authentication){
        CustomUserDetails userDetails=(CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .claim("id",userDetails.getUserId())
                .claim("username",userDetails.getUsername())
                .claim("nickname",userDetails.getNickname())
                .claim("interest",userDetails.getInterest())
                .issuedAt(new Date(System.currentTimeMillis())) //발행 시간
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) //소멸될 시간 설정
                .signWith(secretKey)
                .compact();
    }
}

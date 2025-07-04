package SANTA.backend.global.jwt;

import SANTA.backend.global.security.userinfo.CustomUserDetails;
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

    public String getUserId(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("userId", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }

    //token 생성
    public String createJwt(String username, String role, Long expiredMs) {

        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) //발행 시간
                .expiration(new Date(System.currentTimeMillis() + expiredMs)) //소멸될 시간 설정
                .signWith(secretKey)
                .compact();
    }

    public String createJwt(Authentication authentication){
        CustomUserDetails userDetails=(CustomUserDetails) authentication.getDetails();
        return Jwts.builder()
                .claim("id",userDetails.getUserId())
                .claim("username",userDetails.getUsername())
                .claim("nickname",userDetails.getNickname())
                .issuedAt(new Date(System.currentTimeMillis())) //발행 시간
                .expiration(new Date(System.currentTimeMillis() + 60*60*10)) //소멸될 시간 설정
                .signWith(secretKey)
                .compact();
    }
}

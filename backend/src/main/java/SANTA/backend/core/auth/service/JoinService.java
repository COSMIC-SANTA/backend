package SANTA.backend.core.auth.service;

import SANTA.backend.core.auth.dto.JoinResponseDTO;
import SANTA.backend.core.user.domain.User;
import SANTA.backend.core.user.domain.UserRepository;
import SANTA.backend.global.exception.ErrorCode;
import SANTA.backend.global.exception.type.CustomException;
import SANTA.backend.global.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
public class JoinService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; //config에 있는 encode방식
    private final JWTUtil jwtUtil;
    private final RedisTemplate<String, String> redisTemplate;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JWTUtil jwtUtil, RedisTemplate<String,String> redisTemplate) {
        this.userRepository = userRepository; //초기화 과정
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
        this.redisTemplate = redisTemplate;
    }

    @Transactional
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND, "해당 username의 유저가 존재하지 않습니다:" + username));
    }

    public User findByNickName(String nickname) {
        return userRepository.findByUsername(nickname)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND,"해당 nickname의 유저가 존재하지 않습니다:"+nickname));
    }

    @Transactional
    public JoinResponseDTO join(String username, String password, String nickname, int age) throws IllegalAccessException {
        if (findByUsername(username) != null)
            throw new IllegalAccessException("이미 사용중인 아이디입니다.");

        String encoded = bCryptPasswordEncoder.encode(password);
        User user = User.registerUser(username, encoded, nickname, age);

        User joinUser = userRepository.join(user);
        return new JoinResponseDTO(joinUser);//repository에서 db로의 데이터 저장??
    }

    @CacheEvict(cacheNames = "users", key = "'userId:'+#userId",cacheManager = "cacheManager")
    public void logOut(Long userId, String accessToken) {
        if (accessToken == null || jwtUtil.isExpired(accessToken))
            throw new IllegalArgumentException("유효하지 않은 토큰");
        long expiration = jwtUtil.getExpiration(accessToken) - System.currentTimeMillis();
        redisTemplate.opsForValue().set("blacklist:"+accessToken,"logout", expiration, TimeUnit.MILLISECONDS);
    }
}

package SANTA.backend.core.user.application;

import SANTA.backend.core.user.domain.User;
import SANTA.backend.core.user.domain.UserRepository;
import SANTA.backend.core.user.entity.UserEntity;
import SANTA.backend.global.exception.ErrorCode;
import SANTA.backend.global.exception.type.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Cacheable(cacheNames = "users", key = "'userId:' + #userId", cacheManager = "cacheManager")
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND,"해당 userId의 유저가 존재하지 않습니다: " + userId));
    }

    @Override
    public UserEntity findEntityById(Long userId) {
        return userRepository.findEntityById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND,"해당 userId의 유저가 존재하지 않습니다: " + userId));
    }

    @Override
    public User register(User user) {
        return userRepository.join(user);
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(()->new CustomException(ErrorCode.USER_NOT_FOUND, "해당 username의 유저가 존재하지 않습니다:" + username));
    }
}

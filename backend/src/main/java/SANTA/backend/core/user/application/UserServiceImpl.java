package SANTA.backend.core.user.application;

import SANTA.backend.core.user.domain.User;
import SANTA.backend.core.user.domain.UserRepository;
import SANTA.backend.core.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Cacheable(cacheNames = "users", key = "'userId:'+ #userId", cacheManager = "cacheManager")
    public User findById(Long userId){
        return userRepository.findById(userId);
    }

    @Override
    public UserEntity findEntityById(Long userId) {
        return userRepository.findEntityById(userId);
    }

    @Override
    public User register(User user) {
        return userRepository.join(user);
    }

    @Override
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }
}

package SANTA.backend.core.user.application;

import SANTA.backend.core.user.domain.User;
import SANTA.backend.core.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(Long userId){
        return userRepository.findById(Long.valueOf(userId));
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

package SANTA.backend.core.user.service;

import SANTA.backend.core.user.domain.User;
import SANTA.backend.core.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(String userId){
        return userRepository.findById(Long.valueOf(userId));
    }

    @Override
    public User register(User user) {
        return userRepository.join(user);
    }
}

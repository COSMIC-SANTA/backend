package SANTA.backend.core.user.application;


import SANTA.backend.core.user.domain.User;
import SANTA.backend.core.user.entity.UserEntity;

public interface UserService {
    public User findById(Long userId);
    public UserEntity findEntityById(Long userId);
    public User register(User user);

    User findByUsername(String username);
}

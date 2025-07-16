package SANTA.backend.core.user.application;


import SANTA.backend.core.user.domain.User;

public interface UserService {
    public User findById(String userId);
    public User register(User user);

    User findByUsername(String username);
}

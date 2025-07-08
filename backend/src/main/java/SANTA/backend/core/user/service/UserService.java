package SANTA.backend.core.user.service;


import SANTA.backend.core.user.domain.User;

public interface UserService {
    public User findById(String userId);
    public User register(User user);
}

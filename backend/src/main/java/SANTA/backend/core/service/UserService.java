package SANTA.backend.core.service;


import SANTA.backend.core.domain.User;

public interface UserService {
    public User findById(String userId);
    public User register(User user);
}

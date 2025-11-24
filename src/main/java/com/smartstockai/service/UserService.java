package com.smartstockai.service;

import com.smartstockai.model.User;
import java.util.List;

public interface UserService {
    User createUser(User user);
    User updateUser(User user);
    User getUserById(Long id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    void deleteUser(Long id);
    boolean authenticate(String username, String password);
}


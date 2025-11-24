package com.smartstockai.service;

import com.smartstockai.model.User;

public interface AuthService {
    String login(String username, String password);
    User getCurrentUser();
    String generateToken(User user);
    boolean validateToken(String token);
}


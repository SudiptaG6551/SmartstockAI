package com.smartstockai.dao;

import com.smartstockai.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    User save(User user);
    User update(User user);
    void deleteById(Long id);
    boolean existsByUsername(String username);
}


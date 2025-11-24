package com.smartstockai.dao;

import com.smartstockai.model.Role;
import com.smartstockai.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setEmail(rs.getString("email"));
        String roleName = rs.getString("role_name");
        if (roleName != null) {
            user.setRole(Role.valueOf(roleName));
        }
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        if (rs.getTimestamp("updated_at") != null) {
            user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        user.setActive(rs.getBoolean("active"));
        return user;
    };

    @Override
    public Optional<User> findById(Long id) {
        String sql = """
                SELECT u.*, r.name AS role_name
                FROM users u
                LEFT JOIN roles r ON u.role_id = r.id
                WHERE u.id = ?
                """;
        try {
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        String sql = """
                SELECT u.*, r.name AS role_name
                FROM users u
                LEFT JOIN roles r ON u.role_id = r.id
                WHERE u.username = ?
                """;
        try {
            User user = jdbcTemplate.queryForObject(sql, userRowMapper, username);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> findAll() {
        String sql = """
                SELECT u.*, r.name AS role_name
                FROM users u
                LEFT JOIN roles r ON u.role_id = r.id
                """;
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO users (username, password, email, role_id, created_at, updated_at, active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        LocalDateTime now = LocalDateTime.now();
        Long roleId = getRoleId(user.getRole());
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail(),
                roleId, now, now, user.getActive());

        return findByUsername(user.getUsername()).orElse(user);
    }

    @Override
    public User update(User user) {
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, role_id = ?, updated_at = ?, active = ? WHERE id = ?";
        Long roleId = getRoleId(user.getRole());
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail(),
                roleId, LocalDateTime.now(), user.getActive(), user.getId());
        return findById(user.getId()).orElse(user);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }

    private Long getRoleId(Role role) {
        if (role == null) {
            return null;
        }
        String sql = "SELECT id FROM roles WHERE name = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, role.name());
    }
}


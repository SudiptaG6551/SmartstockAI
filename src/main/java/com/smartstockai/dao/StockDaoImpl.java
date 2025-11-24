package com.smartstockai.dao;

import com.smartstockai.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class StockDaoImpl implements StockDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Stock> stockRowMapper = (rs, rowNum) -> {
        Stock stock = new Stock();
        stock.setId(rs.getLong("id"));
        stock.setProductId(rs.getLong("product_id"));
        stock.setQuantity(rs.getInt("quantity"));
        stock.setReservedQuantity(rs.getInt("reserved_quantity"));
        stock.setMinimumThreshold(rs.getInt("minimum_threshold"));
        if (rs.getTimestamp("last_updated") != null) {
            stock.setLastUpdated(rs.getTimestamp("last_updated").toLocalDateTime());
        }
        stock.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return stock;
    };

    @Override
    public Optional<Stock> findById(Long id) {
        String sql = "SELECT * FROM stock WHERE id = ?";
        try {
            Stock stock = jdbcTemplate.queryForObject(sql, stockRowMapper, id);
            return Optional.ofNullable(stock);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Stock> findByProductId(Long productId) {
        String sql = "SELECT * FROM stock WHERE product_id = ?";
        try {
            Stock stock = jdbcTemplate.queryForObject(sql, stockRowMapper, productId);
            return Optional.ofNullable(stock);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Stock> findAll() {
        String sql = "SELECT * FROM stock";
        return jdbcTemplate.query(sql, stockRowMapper);
    }

    @Override
    public List<Stock> findLowStock(Integer threshold) {
        String sql = "SELECT * FROM stock WHERE quantity <= minimum_threshold";
        return jdbcTemplate.query(sql, stockRowMapper);
    }

    @Override
    public Stock save(Stock stock) {
        String sql = "INSERT INTO stock (product_id, quantity, reserved_quantity, minimum_threshold, last_updated, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        LocalDateTime now = LocalDateTime.now();
        jdbcTemplate.update(sql, stock.getProductId(), stock.getQuantity(), stock.getReservedQuantity(),
                stock.getMinimumThreshold(), now, now);
        return findByProductId(stock.getProductId()).orElse(stock);
    }

    @Override
    public Stock update(Stock stock) {
        String sql = "UPDATE stock SET quantity = ?, reserved_quantity = ?, minimum_threshold = ?, last_updated = ? WHERE id = ?";
        jdbcTemplate.update(sql, stock.getQuantity(), stock.getReservedQuantity(),
                stock.getMinimumThreshold(), LocalDateTime.now(), stock.getId());
        return findById(stock.getId()).orElse(stock);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM stock WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean updateQuantity(Long productId, Integer quantityChange) {
        String sql = "UPDATE stock SET quantity = quantity + ?, last_updated = ? WHERE product_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, quantityChange, LocalDateTime.now(), productId);
        return rowsAffected > 0;
    }
}


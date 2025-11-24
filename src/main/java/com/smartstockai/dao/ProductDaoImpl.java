package com.smartstockai.dao;

import com.smartstockai.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setSku(rs.getString("sku"));
        product.setPurchasePrice(rs.getBigDecimal("purchase_price"));
        product.setSellingPrice(rs.getBigDecimal("selling_price"));
        product.setCategory(rs.getString("category"));
        if (hasColumn(rs, "current_stock")) {
            product.setCurrentStock((Integer) rs.getObject("current_stock"));
        }
        product.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        if (rs.getTimestamp("updated_at") != null) {
            product.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        }
        product.setActive(rs.getBoolean("active"));
        return product;
    };

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try {
            Product product = jdbcTemplate.queryForObject(sql, productRowMapper, id);
            return Optional.ofNullable(product);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Product> findBySku(String sku) {
        String sql = "SELECT * FROM products WHERE sku = ?";
        try {
            Product product = jdbcTemplate.queryForObject(sql, productRowMapper, sku);
            return Optional.ofNullable(product);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM products WHERE active = true";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    @Override
    public List<Product> findByCategory(String category) {
        String sql = "SELECT * FROM products WHERE category = ? AND active = true";
        return jdbcTemplate.query(sql, productRowMapper, category);
    }

    @Override
    public Product save(Product product) {
        String sql = "INSERT INTO products (name, description, sku, purchase_price, selling_price, category, created_at, updated_at, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getSku(),
                product.getPurchasePrice(), product.getSellingPrice(), product.getCategory(),
                LocalDateTime.now(), LocalDateTime.now(), product.getActive());
        return findBySku(product.getSku()).orElse(product);
    }

    @Override
    public Product update(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, sku = ?, purchase_price = ?, selling_price = ?, category = ?, updated_at = ?, active = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getDescription(), product.getSku(),
                product.getPurchasePrice(), product.getSellingPrice(), product.getCategory(),
                LocalDateTime.now(), product.getActive(), product.getId());
        return findById(product.getId()).orElse(product);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "UPDATE products SET active = false WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsBySku(String sku) {
        String sql = "SELECT COUNT(*) FROM products WHERE sku = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, sku);
        return count != null && count > 0;
    }

    @Override
    public void updateStockQuantity(Long productId, Integer currentStock) {
        String sql = "UPDATE products SET available_quantity = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, currentStock, LocalDateTime.now(), productId);
    }

    private boolean hasColumn(java.sql.ResultSet rs, String columnName) {
        try {
            rs.findColumn(columnName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}


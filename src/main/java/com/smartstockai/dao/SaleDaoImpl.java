package com.smartstockai.dao;

import com.smartstockai.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SaleDaoImpl implements SaleDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Sale> saleRowMapper = (rs, rowNum) -> {
        Sale sale = new Sale();
        sale.setId(rs.getLong("id"));
        sale.setProductId(rs.getLong("product_id"));
        sale.setQuantity(rs.getInt("quantity"));
        sale.setUnitPrice(rs.getBigDecimal("unit_price"));
        sale.setTotalAmount(rs.getBigDecimal("total_amount"));
        sale.setProfit(rs.getBigDecimal("profit"));
        sale.setUserId(rs.getLong("user_id"));
        sale.setSaleDate(rs.getTimestamp("sale_date").toLocalDateTime());
        sale.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return sale;
    };

    @Override
    public Sale findById(Long id) {
        String sql = "SELECT * FROM sales WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, saleRowMapper, id);
    }

    @Override
    public List<Sale> findAll() {
        String sql = "SELECT * FROM sales ORDER BY sale_date DESC";
        return jdbcTemplate.query(sql, saleRowMapper);
    }

    @Override
    public List<Sale> findByProductId(Long productId) {
        String sql = "SELECT * FROM sales WHERE product_id = ? ORDER BY sale_date DESC";
        return jdbcTemplate.query(sql, saleRowMapper, productId);
    }

    @Override
    public List<Sale> findByUserId(Long userId) {
        String sql = "SELECT * FROM sales WHERE user_id = ? ORDER BY sale_date DESC";
        return jdbcTemplate.query(sql, saleRowMapper, userId);
    }

    @Override
    public List<Sale> findByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT * FROM sales WHERE DATE(sale_date) BETWEEN ? AND ? ORDER BY sale_date DESC";
        return jdbcTemplate.query(sql, saleRowMapper, startDate, endDate);
    }

    @Override
    public Sale save(Sale sale) {
        String sql = "INSERT INTO sales (product_id, quantity, unit_price, total_amount, profit, user_id, sale_date, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        LocalDateTime now = LocalDateTime.now();
        jdbcTemplate.update(sql, sale.getProductId(), sale.getQuantity(), sale.getUnitPrice(),
                sale.getTotalAmount(), sale.getProfit(), sale.getUserId(), sale.getSaleDate() != null ? sale.getSaleDate() : now, now);
        return sale;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM sales WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public BigDecimal getTotalRevenueByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT COALESCE(SUM(total_amount), 0) FROM sales WHERE DATE(sale_date) BETWEEN ? AND ?";
        BigDecimal result = jdbcTemplate.queryForObject(sql, BigDecimal.class, startDate, endDate);
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getTotalProfitByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT COALESCE(SUM(profit), 0) FROM sales WHERE DATE(sale_date) BETWEEN ? AND ?";
        BigDecimal result = jdbcTemplate.queryForObject(sql, BigDecimal.class, startDate, endDate);
        return result != null ? result : BigDecimal.ZERO;
    }

    @Override
    public Integer getTotalSalesCountByDateRange(LocalDate startDate, LocalDate endDate) {
        String sql = "SELECT COUNT(*) FROM sales WHERE DATE(sale_date) BETWEEN ? AND ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, startDate, endDate);
        return count != null ? count : 0;
    }
}


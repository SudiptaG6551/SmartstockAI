package com.smartstockai.dao;

import com.smartstockai.model.StockTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class StockTransactionDaoImpl implements StockTransactionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<StockTransaction> transactionRowMapper = (rs, rowNum) -> {
        StockTransaction transaction = new StockTransaction();
        transaction.setId(rs.getLong("id"));
        transaction.setProductId(rs.getLong("product_id"));
        transaction.setQuantity(rs.getInt("quantity"));
        transaction.setTransactionType(rs.getString("transaction_type"));
        transaction.setReferenceType(rs.getString("reference_type"));
        transaction.setReferenceId(rs.getLong("reference_id"));
        transaction.setTransactionDate(rs.getTimestamp("transaction_date").toLocalDateTime());
        transaction.setNotes(rs.getString("notes"));
        return transaction;
    };

    @Override
    public StockTransaction findById(Long id) {
        String sql = "SELECT * FROM stock_transactions WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, transactionRowMapper, id);
    }

    @Override
    public List<StockTransaction> findAll() {
        String sql = "SELECT * FROM stock_transactions ORDER BY transaction_date DESC";
        return jdbcTemplate.query(sql, transactionRowMapper);
    }

    @Override
    public List<StockTransaction> findByProductId(Long productId) {
        String sql = "SELECT * FROM stock_transactions WHERE product_id = ? ORDER BY transaction_date DESC";
        return jdbcTemplate.query(sql, transactionRowMapper, productId);
    }

    @Override
    public StockTransaction save(StockTransaction transaction) {
        String sql = "INSERT INTO stock_transactions (product_id, quantity, transaction_type, reference_type, reference_id, transaction_date, notes) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, transaction.getProductId(), transaction.getQuantity(),
                transaction.getTransactionType(), transaction.getReferenceType(),
                transaction.getReferenceId(), transaction.getTransactionDate() != null ? transaction.getTransactionDate() : LocalDateTime.now(),
                transaction.getNotes());
        return transaction;
    }
}


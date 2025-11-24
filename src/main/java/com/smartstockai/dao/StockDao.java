package com.smartstockai.dao;

import com.smartstockai.model.Stock;
import java.util.List;
import java.util.Optional;

public interface StockDao {
    Optional<Stock> findById(Long id);
    Optional<Stock> findByProductId(Long productId);
    List<Stock> findAll();
    List<Stock> findLowStock(Integer threshold);
    Stock save(Stock stock);
    Stock update(Stock stock);
    void deleteById(Long id);
    boolean updateQuantity(Long productId, Integer quantityChange);
}


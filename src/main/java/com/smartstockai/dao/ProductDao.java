package com.smartstockai.dao;

import com.smartstockai.model.Product;
import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> findById(Long id);
    Optional<Product> findBySku(String sku);
    List<Product> findAll();
    List<Product> findByCategory(String category);
    Product save(Product product);
    Product update(Product product);
    void deleteById(Long id);
    boolean existsBySku(String sku);
    void updateStockQuantity(Long productId, Integer currentStock);
}


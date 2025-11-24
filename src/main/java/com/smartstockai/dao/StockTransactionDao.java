package com.smartstockai.dao;

import com.smartstockai.model.StockTransaction;
import java.util.List;

public interface StockTransactionDao {
    StockTransaction findById(Long id);
    List<StockTransaction> findAll();
    List<StockTransaction> findByProductId(Long productId);
    StockTransaction save(StockTransaction transaction);
}


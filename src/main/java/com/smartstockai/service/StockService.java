package com.smartstockai.service;

import com.smartstockai.model.Stock;
import com.smartstockai.model.StockTransaction;
import java.util.List;

public interface StockService {
    Stock createStock(Stock stock);
    Stock updateStock(Stock stock);
    Stock getStockById(Long id);
    Stock getStockByProductId(Long productId);
    List<Stock> getAllStock();
    List<Stock> getLowStock();
    void updateStockQuantity(Long productId, Integer quantityChange);
    List<StockTransaction> getAllTransactions();
}


package com.smartstockai.service;

import com.smartstockai.dao.StockDao;
import com.smartstockai.dao.StockTransactionDao;
import com.smartstockai.model.Stock;
import com.smartstockai.model.StockTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class StockServiceImpl implements StockService {

    @Autowired
    private StockDao stockDao;

    @Autowired
    private StockTransactionDao stockTransactionDao;

    @Override
    public Stock createStock(Stock stock) {
        return stockDao.save(stock);
    }

    @Override
    public Stock updateStock(Stock stock) {
        return stockDao.update(stock);
    }

    @Override
    public Stock getStockById(Long id) {
        return stockDao.findById(id).orElseThrow(() -> new RuntimeException("Stock not found"));
    }

    @Override
    public Stock getStockByProductId(Long productId) {
        return stockDao.findByProductId(productId).orElseThrow(() -> new RuntimeException("Stock not found"));
    }

    @Override
    public List<Stock> getAllStock() {
        return stockDao.findAll();
    }

    @Override
    public List<Stock> getLowStock() {
        return stockDao.findLowStock(0);
    }

    @Override
    public void updateStockQuantity(Long productId, Integer quantityChange) {
        Stock stock = stockDao.findByProductId(productId).orElse(null);
        if (stock == null) {
            if (quantityChange < 0) {
                throw new RuntimeException("Cannot reduce stock for product without existing inventory");
            }
            Stock newStock = new Stock();
            newStock.setProductId(productId);
            newStock.setQuantity(quantityChange);
            newStock.setReservedQuantity(0);
            newStock.setMinimumThreshold(0);
            newStock.setLastUpdated(LocalDateTime.now());
            newStock.setCreatedAt(LocalDateTime.now());
            stockDao.save(newStock);
            
            // Create transaction record for stock IN
            if (quantityChange > 0) {
                createStockTransaction(productId, quantityChange, "IN", "PURCHASE", null, "Stock received");
            }
            return;
        }

        int newQuantity = stock.getQuantity() + quantityChange;
        if (newQuantity < 0) {
            throw new RuntimeException("Insufficient stock");
        }
        stock.setQuantity(newQuantity);
        stock.setLastUpdated(LocalDateTime.now());
        stockDao.update(stock);
        
        // Create transaction record
        if (quantityChange > 0) {
            createStockTransaction(productId, quantityChange, "IN", "PURCHASE", null, "Stock received");
        } else if (quantityChange < 0) {
            createStockTransaction(productId, Math.abs(quantityChange), "OUT", "ADJUSTMENT", null, "Stock adjustment");
        }
    }
    
    private void createStockTransaction(Long productId, Integer quantity, String transactionType, 
                                       String referenceType, Long referenceId, String notes) {
        com.smartstockai.model.StockTransaction transaction = new com.smartstockai.model.StockTransaction();
        transaction.setProductId(productId);
        transaction.setQuantity(quantity);
        transaction.setTransactionType(transactionType);
        transaction.setReferenceType(referenceType);
        transaction.setReferenceId(referenceId);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setNotes(notes);
        stockTransactionDao.save(transaction);
    }

    @Override
    public List<StockTransaction> getAllTransactions() {
        return stockTransactionDao.findAll();
    }
}


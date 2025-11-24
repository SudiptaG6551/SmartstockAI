package com.smartstockai.service;

import com.smartstockai.dao.ProductDao;
import com.smartstockai.dao.SaleDao;
import com.smartstockai.dao.StockDao;
import com.smartstockai.dao.StockTransactionDao;
import com.smartstockai.model.Product;
import com.smartstockai.model.Sale;
import com.smartstockai.model.Stock;
import com.smartstockai.model.StockTransaction;
import com.smartstockai.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleDao saleDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private StockDao stockDao;

    @Autowired
    private StockTransactionDao stockTransactionDao;

    @Autowired
    private UserService userService;

    @Override
    public Sale createSale(Sale sale) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("Unable to determine authenticated user");
        }
        User currentUser = userService.getUserByUsername(authentication.getName());
        sale.setUserId(currentUser.getId());

        Product product = productDao.findById(sale.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        
        Stock stock = stockDao.findByProductId(sale.getProductId())
                .orElseThrow(() -> new RuntimeException("Stock not found"));
        
        if (stock.getQuantity() < sale.getQuantity()) {
            throw new RuntimeException("Insufficient stock");
        }
        
        sale.setUnitPrice(product.getSellingPrice());
        sale.setTotalAmount(product.getSellingPrice().multiply(BigDecimal.valueOf(sale.getQuantity())));
        
        BigDecimal purchaseCost = product.getPurchasePrice().multiply(BigDecimal.valueOf(sale.getQuantity()));
        sale.setProfit(sale.getTotalAmount().subtract(purchaseCost));
        
        if (sale.getSaleDate() == null) {
            sale.setSaleDate(LocalDateTime.now());
        }
        
        Sale savedSale = saleDao.save(sale);
        
        stockDao.updateQuantity(sale.getProductId(), -sale.getQuantity());
        
        StockTransaction transaction = new StockTransaction();
        transaction.setProductId(sale.getProductId());
        transaction.setQuantity(-sale.getQuantity());
        transaction.setTransactionType("OUT");
        transaction.setReferenceType("SALE");
        transaction.setReferenceId(savedSale.getId());
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setNotes("Sale transaction");
        stockTransactionDao.save(transaction);
        
        return savedSale;
    }

    @Override
    public Sale getSaleById(Long id) {
        return saleDao.findById(id);
    }

    @Override
    public List<Sale> getAllSales() {
        return saleDao.findAll();
    }

    @Override
    public List<Sale> getSalesByProductId(Long productId) {
        return saleDao.findByProductId(productId);
    }

    @Override
    public List<Sale> getSalesByUserId(Long userId) {
        return saleDao.findByUserId(userId);
    }

    @Override
    public List<Sale> getSalesByDateRange(LocalDate startDate, LocalDate endDate) {
        return saleDao.findByDateRange(startDate, endDate);
    }

    @Override
    public void deleteSale(Long id) {
        saleDao.deleteById(id);
    }
}


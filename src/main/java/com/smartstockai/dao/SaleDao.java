package com.smartstockai.dao;

import com.smartstockai.model.Sale;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SaleDao {
    Sale findById(Long id);
    List<Sale> findAll();
    List<Sale> findByProductId(Long productId);
    List<Sale> findByUserId(Long userId);
    List<Sale> findByDateRange(LocalDate startDate, LocalDate endDate);
    Sale save(Sale sale);
    void deleteById(Long id);
    BigDecimal getTotalRevenueByDateRange(LocalDate startDate, LocalDate endDate);
    BigDecimal getTotalProfitByDateRange(LocalDate startDate, LocalDate endDate);
    Integer getTotalSalesCountByDateRange(LocalDate startDate, LocalDate endDate);
}


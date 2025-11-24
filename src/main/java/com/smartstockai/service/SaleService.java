package com.smartstockai.service;

import com.smartstockai.model.Sale;
import java.time.LocalDate;
import java.util.List;

public interface SaleService {
    Sale createSale(Sale sale);
    Sale getSaleById(Long id);
    List<Sale> getAllSales();
    List<Sale> getSalesByProductId(Long productId);
    List<Sale> getSalesByUserId(Long userId);
    List<Sale> getSalesByDateRange(LocalDate startDate, LocalDate endDate);
    void deleteSale(Long id);
}


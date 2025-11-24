package com.smartstockai.service;

import com.smartstockai.dao.SaleDao;
import com.smartstockai.dao.StockTransactionDao;
import com.smartstockai.model.ProfitSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OwnerDashboardService {

    private static final BigDecimal DEFAULT_PURCHASE_COST = BigDecimal.valueOf(40.00 * 100); // 100 units at $40

    @Autowired
    private SaleDao saleDao;

    @Autowired
    private StockTransactionDao stockTransactionDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ProfitSummary getProfitSummary() {
        List<com.smartstockai.model.Sale> sales = saleDao.findAll();
        BigDecimal totalIncome = sales.stream()
                .map(com.smartstockai.model.Sale::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalExpense = DEFAULT_PURCHASE_COST;
        BigDecimal netProfit = totalIncome.subtract(totalExpense);

        return new ProfitSummary(totalIncome, totalExpense, netProfit);
    }

    @Transactional
    public void resetDatabase() {
        // Delete all sales
        jdbcTemplate.update("DELETE FROM sales");
        
        // Delete all stock transactions
        jdbcTemplate.update("DELETE FROM stock_transactions");
        
        // Reset stock quantities to initial values (from sample data)
        jdbcTemplate.update("UPDATE stock SET quantity = 151 WHERE product_id = 1");
        jdbcTemplate.update("UPDATE stock SET quantity = 80 WHERE product_id = 2");
        jdbcTemplate.update("UPDATE stock SET quantity = 46 WHERE product_id = 3");
        jdbcTemplate.update("UPDATE stock SET quantity = 0 WHERE product_id = 4");
        jdbcTemplate.update("UPDATE stock SET quantity = 95 WHERE product_id = 5");
        jdbcTemplate.update("UPDATE stock SET quantity = 149 WHERE product_id = 6");
        jdbcTemplate.update("UPDATE stock SET quantity = 80 WHERE product_id = 7");
        jdbcTemplate.update("UPDATE stock SET quantity = 30 WHERE product_id = 8");
        jdbcTemplate.update("UPDATE stock SET quantity = 40 WHERE product_id = 9");
    }
}


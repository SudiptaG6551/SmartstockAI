package com.smartstockai.service;

import com.smartstockai.dao.SaleDao;
import com.smartstockai.model.ProfitExpense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfitExpenseServiceImpl implements ProfitExpenseService {

    @Autowired
    private SaleDao saleDao;

    @Override
    public ProfitExpense getProfitExpenseByDate(LocalDate date) {
        BigDecimal revenue = saleDao.getTotalRevenueByDateRange(date, date);
        BigDecimal profit = saleDao.getTotalProfitByDateRange(date, date);
        BigDecimal expenses = revenue.subtract(profit);
        Integer salesCount = saleDao.getTotalSalesCountByDateRange(date, date);
        
        return new ProfitExpense(date, revenue, expenses, profit, salesCount, salesCount);
    }

    @Override
    public ProfitExpense getProfitExpenseByDateRange(LocalDate startDate, LocalDate endDate) {
        BigDecimal revenue = saleDao.getTotalRevenueByDateRange(startDate, endDate);
        BigDecimal profit = saleDao.getTotalProfitByDateRange(startDate, endDate);
        BigDecimal expenses = revenue.subtract(profit);
        Integer salesCount = saleDao.getTotalSalesCountByDateRange(startDate, endDate);
        
        return new ProfitExpense(startDate, revenue, expenses, profit, salesCount, salesCount);
    }

    @Override
    public List<ProfitExpense> getProfitExpenseReport(LocalDate startDate, LocalDate endDate) {
        List<ProfitExpense> report = new ArrayList<>();
        LocalDate currentDate = startDate;
        
        while (!currentDate.isAfter(endDate)) {
            report.add(getProfitExpenseByDate(currentDate));
            currentDate = currentDate.plusDays(1);
        }
        
        return report;
    }
}


package com.smartstockai.ml;

import com.smartstockai.dao.SaleDao;
import com.smartstockai.model.Sale;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class DemandForecastingService {

    @Autowired
    private SaleDao saleDao;

    public double forecastDemand(Long productId, int daysAhead) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(29);

        List<Sale> sales = saleDao.findByProductId(productId);
        if (sales == null || sales.isEmpty()) {
            return 0.0;
        }

        Map<LocalDate, Integer> dailyTotals = sales.stream()
                .filter(sale -> sale.getSaleDate() != null)
                .map(sale -> Map.entry(sale.getSaleDate().toLocalDate(), sale.getQuantity()))
                .filter(entry -> !entry.getKey().isBefore(startDate) && !entry.getKey().isAfter(today))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum,
                        TreeMap::new
                ));

        if (dailyTotals.size() < 2) {
            return dailyTotals.values().stream().mapToDouble(Integer::doubleValue).average().orElse(0.0);
        }

        SimpleRegression regression = new SimpleRegression();
        int index = 0;
        for (Integer total : dailyTotals.values()) {
            regression.addData(index++, total);
        }

        double predictIndex = index + Math.max(daysAhead, 1);
        double prediction = regression.predict(predictIndex);
        if (Double.isNaN(prediction) || prediction < 0) {
            return 0.0;
        }
        return prediction;
    }

    public double forecastDemandForDateRange(Long productId, LocalDate startDate, LocalDate endDate) {
        List<Sale> sales = saleDao.findByProductId(productId);
        
        if (sales.isEmpty()) {
            return 0.0;
        }
        
        SimpleRegression regression = new SimpleRegression();
        
        int dayIndex = 0;
        for (Sale sale : sales) {
            if (sale.getSaleDate().toLocalDate().isAfter(startDate.minusDays(1)) && 
                sale.getSaleDate().toLocalDate().isBefore(endDate.plusDays(1))) {
                regression.addData(dayIndex++, sale.getQuantity());
            }
        }
        
        if (regression.getN() < 2) {
            return 0.0;
        }
        
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        return Math.max(0, regression.predict(dayIndex + daysBetween));
    }
}


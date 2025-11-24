package com.smartstockai.ml;

import com.smartstockai.dao.SaleDao;
import com.smartstockai.model.Sale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AnomalyDetectionService {

    @Autowired
    private SaleDao saleDao;

    private static final double Z_SCORE_THRESHOLD = 2.5;

    public List<Sale> detectAnomalies(Long productId) {
        List<Sale> sales = saleDao.findByProductId(productId);
        
        if (sales.size() < 3) {
            return new ArrayList<>();
        }
        
        double[] quantities = sales.stream()
                .mapToInt(Sale::getQuantity)
                .mapToDouble(x -> x)
                .toArray();
        
        double mean = calculateMean(quantities);
        double stdDev = calculateStandardDeviation(quantities, mean);
        
        if (stdDev == 0) {
            return new ArrayList<>();
        }
        
        List<Sale> anomalies = new ArrayList<>();
        for (int i = 0; i < sales.size(); i++) {
            double zScore = Math.abs((quantities[i] - mean) / stdDev);
            if (zScore > Z_SCORE_THRESHOLD) {
                anomalies.add(sales.get(i));
            }
        }
        
        return anomalies;
    }

    public List<Sale> detectAnomaliesByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Sale> sales = saleDao.findByDateRange(startDate, endDate);
        
        if (sales.size() < 3) {
            return new ArrayList<>();
        }
        
        double[] amounts = sales.stream()
                .map(sale -> sale.getTotalAmount().doubleValue())
                .mapToDouble(x -> x)
                .toArray();
        
        double mean = calculateMean(amounts);
        double stdDev = calculateStandardDeviation(amounts, mean);
        
        if (stdDev == 0) {
            return new ArrayList<>();
        }
        
        List<Sale> anomalies = new ArrayList<>();
        for (int i = 0; i < sales.size(); i++) {
            double zScore = Math.abs((amounts[i] - mean) / stdDev);
            if (zScore > Z_SCORE_THRESHOLD) {
                anomalies.add(sales.get(i));
            }
        }
        
        return anomalies;
    }

    private double calculateMean(double[] values) {
        double sum = 0.0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    private double calculateStandardDeviation(double[] values, double mean) {
        double sumSquaredDiff = 0.0;
        for (double value : values) {
            double diff = value - mean;
            sumSquaredDiff += diff * diff;
        }
        return Math.sqrt(sumSquaredDiff / values.length);
    }
}


package com.smartstockai.ml;

import com.smartstockai.dao.ProductDao;
import com.smartstockai.dao.SaleDao;
import com.smartstockai.model.Product;
import com.smartstockai.model.Sale;
import smile.clustering.KMeans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KMeansClusteringService {

    @Autowired
    private SaleDao saleDao;

    @Autowired
    private ProductDao productDao;

    public Map<Integer, List<Long>> clusterProductsBySales(int k) {
        List<Product> products = productDao.findAll();
        
        if (products.size() < k) {
            return new HashMap<>();
        }
        
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(90);
        
        List<double[]> features = new ArrayList<>();
        List<Long> productIds = new ArrayList<>();
        
        for (Product product : products) {
            List<Sale> sales = saleDao.findByProductId(product.getId());
            
            double totalSales = sales.stream()
                    .filter(sale -> sale.getSaleDate().toLocalDate().isAfter(startDate.minusDays(1)))
                    .mapToDouble(sale -> sale.getTotalAmount().doubleValue())
                    .sum();
            
            double avgQuantity = sales.stream()
                    .filter(sale -> sale.getSaleDate().toLocalDate().isAfter(startDate.minusDays(1)))
                    .mapToInt(Sale::getQuantity)
                    .average()
                    .orElse(0.0);
            
            features.add(new double[]{totalSales, avgQuantity});
            productIds.add(product.getId());
        }
        
        if (features.size() < k) {
            return new HashMap<>();
        }
        
        double[][] featureArray = features.toArray(new double[0][]);
        KMeans kmeans = KMeans.fit(featureArray, k);
        
        Map<Integer, List<Long>> clusters = new HashMap<>();
        for (int i = 0; i < productIds.size(); i++) {
            int clusterId = kmeans.y[i];
            clusters.computeIfAbsent(clusterId, key -> new ArrayList<>()).add(productIds.get(i));
        }
        
        return clusters;
    }
}


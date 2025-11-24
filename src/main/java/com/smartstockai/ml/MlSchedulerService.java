package com.smartstockai.ml;

import com.smartstockai.dao.ProductDao;
import com.smartstockai.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Service
public class MlSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(MlSchedulerService.class);

    @Autowired
    private DemandForecastingService demandForecastingService;

    @Autowired
    private AnomalyDetectionService anomalyDetectionService;

    @Autowired
    private KMeansClusteringService kMeansClusteringService;

    @Autowired
    private ProductDao productDao;

    @Scheduled(cron = "0 0 2 * * ?")
    public void runDailyMlTasks() {
        logger.info("Starting daily ML tasks at {}", LocalDate.now());
        
        try {
            runDemandForecasting();
            runAnomalyDetection();
            runKMeansClustering();
            
            logger.info("Completed daily ML tasks successfully");
        } catch (Exception e) {
            logger.error("Error running daily ML tasks", e);
        }
    }

    private void runDemandForecasting() {
        logger.info("Running demand forecasting for all products");
        List<Product> products = productDao.findAll();
        
        for (Product product : products) {
            try {
                double forecast = demandForecastingService.forecastDemand(product.getId(), 7);
                logger.debug("Product {} - 7-day forecast: {}", product.getId(), forecast);
            } catch (Exception e) {
                logger.warn("Error forecasting demand for product {}", product.getId(), e);
            }
        }
    }

    private void runAnomalyDetection() {
        logger.info("Running anomaly detection for all products");
        List<Product> products = productDao.findAll();
        
        for (Product product : products) {
            try {
                var anomalies = anomalyDetectionService.detectAnomalies(product.getId());
                if (!anomalies.isEmpty()) {
                    logger.warn("Found {} anomalies for product {}", anomalies.size(), product.getId());
                }
            } catch (Exception e) {
                logger.warn("Error detecting anomalies for product {}", product.getId(), e);
            }
        }
    }

    private void runKMeansClustering() {
        logger.info("Running k-means clustering");
        try {
            var clusters = kMeansClusteringService.clusterProductsBySales(3);
            logger.info("K-means clustering completed. Found {} clusters", clusters.size());
        } catch (Exception e) {
            logger.warn("Error running k-means clustering", e);
        }
    }
}


package com.smartstockai.controller;

import com.smartstockai.ml.DemandForecastingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/predictions")
public class PredictionController {

    @Autowired
    private DemandForecastingService demandForecastingService;

    @GetMapping("/forecast/{productId}")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<Map<String, Object>> forecast(@PathVariable Long productId) {
        double prediction = demandForecastingService.forecastDemand(productId, 1);
        return ResponseEntity.ok(Map.of(
                "productId", productId,
                "predictedQuantity", prediction
        ));
    }
}


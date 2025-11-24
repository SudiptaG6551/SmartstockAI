package com.smartstockai.controller;

import com.smartstockai.model.ProfitSummary;
import com.smartstockai.service.OwnerDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/owner")
public class OwnerDashboardController {

    @Autowired
    private OwnerDashboardService ownerDashboardService;

    @GetMapping("/summary")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<ProfitSummary> getProfitSummary() {
        return ResponseEntity.ok(ownerDashboardService.getProfitSummary());
    }

    @PostMapping("/reset-database")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<Map<String, String>> resetDatabase() {
        try {
            ownerDashboardService.resetDatabase();
            Map<String, String> response = new HashMap<>();
            response.put("message", "Database reset successfully. All sales and transactions have been cleared.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "Failed to reset database: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}


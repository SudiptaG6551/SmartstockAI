package com.smartstockai.controller;

import com.smartstockai.model.Stock;
import com.smartstockai.model.StockRequest;
import com.smartstockai.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    @Autowired
    private StockService stockService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_STOCK_MANAGER')")
    public ResponseEntity<Stock> createStock(@RequestBody Stock stock) {
        Stock created = stockService.createStock(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockService.getStockById(id);
        return ResponseEntity.ok(stock);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Stock> getStockByProductId(@PathVariable Long productId) {
        Stock stock = stockService.getStockByProductId(productId);
        return ResponseEntity.ok(stock);
    }

    @GetMapping
    public ResponseEntity<List<Stock>> getAllStock() {
        List<Stock> stockList = stockService.getAllStock();
        return ResponseEntity.ok(stockList);
    }

    @GetMapping("/low-stock")
    @PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_STOCK_MANAGER')")
    public ResponseEntity<List<Stock>> getLowStock() {
        List<Stock> lowStock = stockService.getLowStock();
        return ResponseEntity.ok(lowStock);
    }

    @PostMapping("/receive")
    @PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_STOCK_MANAGER')")
    public ResponseEntity<Void> receiveStock(@RequestBody StockRequest request) {
        stockService.updateStockQuantity(request.getProductId(), request.getQuantity());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_OWNER', 'ROLE_STOCK_MANAGER')")
    public ResponseEntity<Stock> updateStock(@PathVariable Long id, @RequestBody Stock stock) {
        stock.setId(id);
        Stock updated = stockService.updateStock(stock);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<com.smartstockai.model.StockTransaction>> getAllTransactions() {
        List<com.smartstockai.model.StockTransaction> transactions = stockService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}


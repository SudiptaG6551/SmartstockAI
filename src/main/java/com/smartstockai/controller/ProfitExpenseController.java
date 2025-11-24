package com.smartstockai.controller;

import com.smartstockai.model.ProfitExpense;
import com.smartstockai.service.ProfitExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/profit-expense")
public class ProfitExpenseController {

    @Autowired
    private ProfitExpenseService profitExpenseService;

    @GetMapping("/date/{date}")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<ProfitExpense> getProfitExpenseByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        ProfitExpense profitExpense = profitExpenseService.getProfitExpenseByDate(date);
        return ResponseEntity.ok(profitExpense);
    }

    @GetMapping("/date-range")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<ProfitExpense> getProfitExpenseByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        ProfitExpense profitExpense = profitExpenseService.getProfitExpenseByDateRange(startDate, endDate);
        return ResponseEntity.ok(profitExpense);
    }

    @GetMapping("/report")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<List<ProfitExpense>> getProfitExpenseReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<ProfitExpense> report = profitExpenseService.getProfitExpenseReport(startDate, endDate);
        return ResponseEntity.ok(report);
    }
}


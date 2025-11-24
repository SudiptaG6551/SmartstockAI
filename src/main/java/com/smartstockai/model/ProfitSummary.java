package com.smartstockai.model;

import java.math.BigDecimal;

public class ProfitSummary {

    private BigDecimal totalIncome;
    private BigDecimal totalExpense;
    private BigDecimal netProfit;

    public ProfitSummary(BigDecimal totalIncome, BigDecimal totalExpense, BigDecimal netProfit) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.netProfit = netProfit;
    }

    public BigDecimal getTotalIncome() {
        return totalIncome;
    }

    public BigDecimal getTotalExpense() {
        return totalExpense;
    }

    public BigDecimal getNetProfit() {
        return netProfit;
    }
}


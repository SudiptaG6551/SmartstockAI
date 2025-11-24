package com.smartstockai.service;

import com.smartstockai.model.ProfitExpense;
import java.time.LocalDate;
import java.util.List;

public interface ProfitExpenseService {
    ProfitExpense getProfitExpenseByDate(LocalDate date);
    ProfitExpense getProfitExpenseByDateRange(LocalDate startDate, LocalDate endDate);
    List<ProfitExpense> getProfitExpenseReport(LocalDate startDate, LocalDate endDate);
}


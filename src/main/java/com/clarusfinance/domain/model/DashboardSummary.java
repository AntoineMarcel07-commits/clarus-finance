package com.clarusfinance.domain.model;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.List;

public record DashboardSummary(
        YearMonth period,
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal balance,
        BigDecimal totalBudget,
        int budgetUsagePercentage,
        List<Movement> recentMovements) {

    public DashboardSummary {
        recentMovements = List.copyOf(recentMovements);
    }
}

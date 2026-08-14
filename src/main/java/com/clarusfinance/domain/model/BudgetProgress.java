package com.clarusfinance.domain.model;

import java.math.BigDecimal;

public record BudgetProgress(
        Budget budget,
        BigDecimal spent,
        BigDecimal remaining,
        int percentage,
        BudgetStatus status) {
}

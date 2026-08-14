package com.clarusfinance.application.service;

import com.clarusfinance.domain.model.Budget;
import com.clarusfinance.domain.model.DashboardSummary;
import com.clarusfinance.domain.model.Movement;
import com.clarusfinance.domain.model.MovementType;
import com.clarusfinance.support.TestRepositories;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DashboardServiceTest {

    @Test
    void summarizesIncomeExpensesBalanceAndBudgetUsage() {
        TestRepositories.Movements movements = new TestRepositories.Movements();
        TestRepositories.Budgets budgets = new TestRepositories.Budgets();
        YearMonth period = YearMonth.of(2026, 4);
        movements.save(new Movement(null, MovementType.INCOME, new BigDecimal("2000"),
                "Sueldo", LocalDate.of(2026, 4, 1), ""));
        movements.save(new Movement(null, MovementType.EXPENSE, new BigDecimal("500"),
                "Vivienda", LocalDate.of(2026, 4, 2), ""));
        movements.save(new Movement(null, MovementType.EXPENSE, new BigDecimal("90"),
                "Otros", LocalDate.of(2026, 3, 30), "Fuera del periodo"));
        budgets.save(new Budget(null, "Vivienda", new BigDecimal("1000"), period));

        DashboardSummary summary = new DashboardService(movements, budgets).summarize(period);

        assertEquals(new BigDecimal("2000"), summary.totalIncome());
        assertEquals(new BigDecimal("500"), summary.totalExpense());
        assertEquals(new BigDecimal("1500"), summary.balance());
        assertEquals(new BigDecimal("1000"), summary.totalBudget());
        assertEquals(50, summary.budgetUsagePercentage());
        assertEquals(3, summary.recentMovements().size());
    }

    @Test
    void handlesPeriodWithoutBudgets() {
        DashboardSummary summary = new DashboardService(
                new TestRepositories.Movements(), new TestRepositories.Budgets())
                .summarize(YearMonth.of(2026, 5));

        assertEquals(0, summary.budgetUsagePercentage());
        assertEquals(BigDecimal.ZERO, summary.balance());
    }
}

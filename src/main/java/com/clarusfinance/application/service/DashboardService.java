package com.clarusfinance.application.service;

import com.clarusfinance.domain.model.Budget;
import com.clarusfinance.domain.model.DashboardSummary;
import com.clarusfinance.domain.model.Movement;
import com.clarusfinance.domain.model.MovementType;
import com.clarusfinance.domain.repository.BudgetRepository;
import com.clarusfinance.domain.repository.MovementRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

public final class DashboardService {
    private final MovementRepository movementRepository;
    private final BudgetRepository budgetRepository;

    public DashboardService(MovementRepository movementRepository, BudgetRepository budgetRepository) {
        this.movementRepository = Objects.requireNonNull(movementRepository);
        this.budgetRepository = Objects.requireNonNull(budgetRepository);
    }

    public DashboardSummary summarize(YearMonth period) {
        List<Movement> movements = movementRepository.findBetween(period.atDay(1), period.atEndOfMonth());
        BigDecimal income = sumByType(movements, MovementType.INCOME);
        BigDecimal expense = sumByType(movements, MovementType.EXPENSE);
        BigDecimal totalBudget = budgetRepository.findByPeriod(period).stream()
                .map(Budget::monthlyLimit)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        int usage = totalBudget.signum() == 0 ? 0
                : expense.divide(totalBudget, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100))
                        .setScale(0, RoundingMode.HALF_UP)
                        .intValue();
        return new DashboardSummary(
                period,
                income,
                expense,
                income.subtract(expense),
                totalBudget,
                usage,
                movementRepository.findRecent(8));
    }

    private BigDecimal sumByType(List<Movement> movements, MovementType type) {
        return movements.stream()
                .filter(movement -> movement.type() == type)
                .map(Movement::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

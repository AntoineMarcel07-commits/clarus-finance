package com.clarusfinance.application.service;

import com.clarusfinance.application.exception.ValidationException;
import com.clarusfinance.domain.model.Budget;
import com.clarusfinance.domain.model.BudgetProgress;
import com.clarusfinance.domain.model.BudgetStatus;
import com.clarusfinance.domain.model.Movement;
import com.clarusfinance.domain.model.MovementType;
import com.clarusfinance.domain.repository.BudgetRepository;
import com.clarusfinance.domain.repository.MovementRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

public final class BudgetService {
    private static final BigDecimal WARNING_THRESHOLD = new BigDecimal("0.80");

    private final BudgetRepository budgetRepository;
    private final MovementRepository movementRepository;

    public BudgetService(BudgetRepository budgetRepository, MovementRepository movementRepository) {
        this.budgetRepository = Objects.requireNonNull(budgetRepository);
        this.movementRepository = Objects.requireNonNull(movementRepository);
    }

    public Budget create(String category, BigDecimal limit, YearMonth period) {
        return budgetRepository.save(build(null, category, limit, period));
    }

    public Budget update(long id, String category, BigDecimal limit, YearMonth period) {
        if (budgetRepository.findById(id).isEmpty()) {
            throw new ValidationException("El presupuesto ya no existe");
        }
        return budgetRepository.save(build(id, category, limit, period));
    }

    public void delete(long id) {
        if (budgetRepository.findById(id).isEmpty()) {
            throw new ValidationException("El presupuesto ya no existe");
        }
        budgetRepository.deleteById(id);
    }

    public List<BudgetProgress> progressFor(YearMonth period) {
        List<Movement> movements = movementRepository.findBetween(period.atDay(1), period.atEndOfMonth());
        return budgetRepository.findByPeriod(period).stream()
                .map(budget -> progress(budget, movements))
                .toList();
    }

    private BudgetProgress progress(Budget budget, List<Movement> movements) {
        BigDecimal spent = movements.stream()
                .filter(movement -> movement.type() == MovementType.EXPENSE)
                .filter(movement -> movement.category().equalsIgnoreCase(budget.category()))
                .map(Movement::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal ratio = spent.divide(budget.monthlyLimit(), 4, RoundingMode.HALF_UP);
        int percentage = ratio.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).intValue();
        BudgetStatus status = spent.compareTo(budget.monthlyLimit()) > 0
                ? BudgetStatus.EXCEEDED
                : ratio.compareTo(WARNING_THRESHOLD) >= 0 ? BudgetStatus.WARNING : BudgetStatus.HEALTHY;
        return new BudgetProgress(
                budget,
                spent,
                budget.monthlyLimit().subtract(spent),
                percentage,
                status);
    }

    private Budget build(Long id, String category, BigDecimal limit, YearMonth period) {
        try {
            return new Budget(id, category, limit, period);
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw new ValidationException(exception.getMessage(), exception);
        }
    }
}

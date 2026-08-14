package com.clarusfinance.application.service;

import com.clarusfinance.application.exception.ValidationException;
import com.clarusfinance.domain.model.Budget;
import com.clarusfinance.domain.model.BudgetProgress;
import com.clarusfinance.domain.model.BudgetStatus;
import com.clarusfinance.domain.model.Movement;
import com.clarusfinance.domain.model.MovementType;
import com.clarusfinance.support.TestRepositories;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BudgetServiceTest {
    private static final YearMonth PERIOD = YearMonth.of(2026, 3);
    private TestRepositories.Movements movements;
    private TestRepositories.Budgets budgets;
    private BudgetService service;

    @BeforeEach
    void setUp() {
        movements = new TestRepositories.Movements();
        budgets = new TestRepositories.Budgets();
        service = new BudgetService(budgets, movements);
    }

    @Test
    void calculatesHealthyProgressUsingExpensesOnly() {
        Budget budget = service.create("Alimentación", new BigDecimal("1000"), PERIOD);
        movements.save(new Movement(null, MovementType.EXPENSE, new BigDecimal("250"),
                "Alimentación", LocalDate.of(2026, 3, 4), ""));
        movements.save(new Movement(null, MovementType.INCOME, new BigDecimal("800"),
                "Alimentación", LocalDate.of(2026, 3, 5), ""));

        BudgetProgress progress = service.progressFor(PERIOD).get(0);

        assertEquals(budget.id(), progress.budget().id());
        assertEquals(new BigDecimal("250"), progress.spent());
        assertEquals(25, progress.percentage());
        assertEquals(BudgetStatus.HEALTHY, progress.status());
    }

    @Test
    void marksWarningAtEightyPercent() {
        service.create("Transporte", new BigDecimal("1000"), PERIOD);
        movements.save(new Movement(null, MovementType.EXPENSE, new BigDecimal("800"),
                "Transporte", LocalDate.of(2026, 3, 4), ""));

        assertEquals(BudgetStatus.WARNING, service.progressFor(PERIOD).get(0).status());
    }

    @Test
    void marksExceededOverOneHundredPercent() {
        service.create("Salud", new BigDecimal("1000"), PERIOD);
        movements.save(new Movement(null, MovementType.EXPENSE, new BigDecimal("1000.01"),
                "Salud", LocalDate.of(2026, 3, 4), ""));

        BudgetProgress progress = service.progressFor(PERIOD).get(0);
        assertEquals(BudgetStatus.EXCEEDED, progress.status());
        assertEquals(new BigDecimal("-0.01"), progress.remaining());
    }

    @Test
    void rejectsInvalidLimitAndMissingUpdate() {
        assertThrows(ValidationException.class, () ->
                service.create("Otros", BigDecimal.ZERO, PERIOD));
        assertThrows(ValidationException.class, () ->
                service.update(99, "Otros", BigDecimal.TEN, PERIOD));
    }
}

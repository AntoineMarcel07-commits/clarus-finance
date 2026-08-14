package com.clarusfinance.application.service;

import com.clarusfinance.application.exception.ValidationException;
import com.clarusfinance.domain.model.Movement;
import com.clarusfinance.domain.model.MovementType;
import com.clarusfinance.support.TestRepositories;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MovementServiceTest {
    private TestRepositories.Movements repository;
    private MovementService service;

    @BeforeEach
    void setUp() {
        repository = new TestRepositories.Movements();
        service = new MovementService(repository);
    }

    @Test
    void createsAValidMovement() {
        Movement movement = service.create(MovementType.EXPENSE, new BigDecimal("350.50"),
                "Alimentación", LocalDate.of(2026, 1, 15), "Supermercado");

        assertEquals(1L, movement.id());
        assertEquals(new BigDecimal("350.50"), movement.amount());
        assertEquals("Alimentación", movement.category());
    }

    @Test
    void rejectsNonPositiveAmount() {
        ValidationException error = assertThrows(ValidationException.class, () ->
                service.create(MovementType.EXPENSE, BigDecimal.ZERO,
                        "Transporte", LocalDate.of(2026, 1, 1), ""));

        assertEquals("El monto debe ser mayor a cero", error.getMessage());
    }

    @Test
    void rejectsFutureDate() {
        assertThrows(ValidationException.class, () ->
                service.create(MovementType.INCOME, BigDecimal.TEN,
                        "Otros", LocalDate.now().plusDays(1), ""));
    }

    @Test
    void updatesExistingMovement() {
        Movement saved = service.create(MovementType.EXPENSE, BigDecimal.TEN,
                "Otros", LocalDate.of(2026, 1, 2), "Antes");

        Movement updated = service.update(saved.id(), MovementType.INCOME, new BigDecimal("20"),
                "Sueldo", LocalDate.of(2026, 1, 3), "Después");

        assertEquals(MovementType.INCOME, updated.type());
        assertEquals("Después", updated.description());
    }

    @Test
    void cannotUpdateMissingMovement() {
        assertThrows(ValidationException.class, () ->
                service.update(99, MovementType.EXPENSE, BigDecimal.TEN,
                        "Otros", LocalDate.of(2026, 1, 1), ""));
    }

    @Test
    void listsOnlySelectedMonthAndDeletes() {
        Movement january = service.create(MovementType.EXPENSE, BigDecimal.ONE,
                "Otros", LocalDate.of(2026, 1, 3), "Enero");
        service.create(MovementType.EXPENSE, BigDecimal.ONE,
                "Otros", LocalDate.of(2026, 2, 3), "Febrero");

        assertEquals(1, service.list(YearMonth.of(2026, 1)).size());
        service.delete(january.id());
        assertFalse(repository.findById(january.id()).isPresent());
    }
}

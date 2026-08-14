package com.clarusfinance.application.service;

import com.clarusfinance.application.exception.ValidationException;
import com.clarusfinance.domain.model.Movement;
import com.clarusfinance.domain.model.MovementType;
import com.clarusfinance.domain.repository.MovementRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Objects;

public final class MovementService {
    private final MovementRepository repository;

    public MovementService(MovementRepository repository) {
        this.repository = Objects.requireNonNull(repository);
    }

    public Movement create(MovementType type, BigDecimal amount, String category,
            LocalDate date, String description) {
        return repository.save(build(null, type, amount, category, date, description));
    }

    public Movement update(long id, MovementType type, BigDecimal amount, String category,
            LocalDate date, String description) {
        if (repository.findById(id).isEmpty()) {
            throw new ValidationException("El movimiento ya no existe");
        }
        return repository.save(build(id, type, amount, category, date, description));
    }

    public List<Movement> list(YearMonth period) {
        Objects.requireNonNull(period, "El periodo es obligatorio");
        return repository.findBetween(period.atDay(1), period.atEndOfMonth());
    }

    public void delete(long id) {
        if (repository.findById(id).isEmpty()) {
            throw new ValidationException("El movimiento ya no existe");
        }
        repository.deleteById(id);
    }

    private Movement build(Long id, MovementType type, BigDecimal amount, String category,
            LocalDate date, String description) {
        try {
            if (date != null && date.isAfter(LocalDate.now())) {
                throw new ValidationException("La fecha no puede estar en el futuro");
            }
            return new Movement(id, type, amount, category, date, description);
        } catch (IllegalArgumentException | NullPointerException exception) {
            throw new ValidationException(exception.getMessage(), exception);
        }
    }
}

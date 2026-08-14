package com.clarusfinance.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public record Movement(
        Long id,
        MovementType type,
        BigDecimal amount,
        String category,
        LocalDate date,
        String description) {

    public Movement {
        Objects.requireNonNull(type, "El tipo es obligatorio");
        Objects.requireNonNull(amount, "El monto es obligatorio");
        Objects.requireNonNull(date, "La fecha es obligatoria");
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero");
        }
        category = requireText(category, "La categoría es obligatoria");
        description = description == null ? "" : description.trim();
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }
}

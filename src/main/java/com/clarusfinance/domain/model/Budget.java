package com.clarusfinance.domain.model;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Objects;

public record Budget(Long id, String category, BigDecimal monthlyLimit, YearMonth period) {

    public Budget {
        category = category == null ? "" : category.trim();
        if (category.isBlank()) {
            throw new IllegalArgumentException("La categoría es obligatoria");
        }
        Objects.requireNonNull(monthlyLimit, "El límite es obligatorio");
        Objects.requireNonNull(period, "El periodo es obligatorio");
        if (monthlyLimit.signum() <= 0) {
            throw new IllegalArgumentException("El límite debe ser mayor a cero");
        }
    }
}

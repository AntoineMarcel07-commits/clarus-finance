package com.clarusfinance.domain.model;

public enum MovementType {
    INCOME("Ingreso"),
    EXPENSE("Gasto");

    private final String displayName;

    MovementType(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

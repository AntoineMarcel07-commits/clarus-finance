package com.clarusfinance.domain.model;

public enum BudgetStatus {
    HEALTHY("Disponible"),
    WARNING("Por alcanzar"),
    EXCEEDED("Excedido");

    private final String displayName;

    BudgetStatus(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}

package com.clarusfinance.modelo;

public enum TipoMovimiento {
    INGRESO("Ingreso"),
    GASTO("Gasto");

    private final String texto;

    TipoMovimiento(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
}

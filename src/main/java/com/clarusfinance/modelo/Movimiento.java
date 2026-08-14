package com.clarusfinance.modelo;

import java.time.LocalDate;

public class Movimiento {
    private final int id;
    private final LocalDate fecha;
    private final TipoMovimiento tipo;
    private final String categoria;
    private final String descripcion;
    private final double monto;

    public Movimiento(int id, LocalDate fecha, TipoMovimiento tipo,
            String categoria, String descripcion, double monto) {
        this.id = id;
        this.fecha = fecha;
        this.tipo = tipo;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public int getId() {
        return id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getMonto() {
        return monto;
    }
}

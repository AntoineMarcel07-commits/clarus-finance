package com.clarusfinance.servicio;

import com.clarusfinance.datos.MovimientoRepositorio;
import com.clarusfinance.modelo.Movimiento;
import com.clarusfinance.modelo.TipoMovimiento;
import java.time.LocalDate;
import java.util.List;

public class FinanzasServicio {
    private final MovimientoRepositorio repositorio;

    public FinanzasServicio(MovimientoRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public Movimiento registrar(String descripcion, String categoria,
            TipoMovimiento tipo, double monto) {
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("Escribe una descripción.");
        }
        if (categoria == null || categoria.isBlank()) {
            throw new IllegalArgumentException("Escribe una categoría.");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("Selecciona un tipo.");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }

        Movimiento movimiento = new Movimiento(siguienteId(), LocalDate.now(), tipo,
                categoria.trim(), descripcion.trim(), monto);
        repositorio.agregar(movimiento);
        return movimiento;
    }

    public List<Movimiento> listar() {
        return repositorio.listar();
    }

    public void eliminar(int id) {
        if (!repositorio.eliminarPorId(id)) {
            throw new IllegalArgumentException("No se encontró el movimiento.");
        }
    }

    public double totalIngresos() {
        return sumar(TipoMovimiento.INGRESO);
    }

    public double totalGastos() {
        return sumar(TipoMovimiento.GASTO);
    }

    public double saldo() {
        return totalIngresos() - totalGastos();
    }

    private double sumar(TipoMovimiento tipo) {
        double total = 0;
        for (Movimiento movimiento : repositorio.listar()) {
            if (movimiento.getTipo() == tipo) {
                total += movimiento.getMonto();
            }
        }
        return total;
    }

    private int siguienteId() {
        int mayor = 0;
        for (Movimiento movimiento : repositorio.listar()) {
            if (movimiento.getId() > mayor) {
                mayor = movimiento.getId();
            }
        }
        return mayor + 1;
    }
}

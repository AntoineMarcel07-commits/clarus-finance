package com.clarusfinance.servicio;

import com.clarusfinance.datos.MovimientoRepositorio;
import com.clarusfinance.modelo.Movimiento;
import com.clarusfinance.modelo.TipoMovimiento;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FinanzasServicioTest {
    private FinanzasServicio servicio;

    @BeforeEach
    void preparar() {
        servicio = new FinanzasServicio(new RepositorioDePrueba());
    }

    @Test
    void registraUnIngreso() {
        Movimiento movimiento = servicio.registrar(
                "Pago de trabajo", "Trabajo", TipoMovimiento.INGRESO, 500);

        assertEquals(1, movimiento.getId());
        assertEquals(500, servicio.totalIngresos(), 0.001);
    }

    @Test
    void registraUnGasto() {
        servicio.registrar("Comida", "Alimentos", TipoMovimiento.GASTO, 120);

        assertEquals(120, servicio.totalGastos(), 0.001);
    }

    @Test
    void calculaElSaldo() {
        servicio.registrar("Pago", "Trabajo", TipoMovimiento.INGRESO, 1000);
        servicio.registrar("Transporte", "Pasajes", TipoMovimiento.GASTO, 250);

        assertEquals(750, servicio.saldo(), 0.001);
    }

    @Test
    void asignaIdsConsecutivos() {
        Movimiento primero = servicio.registrar("Uno", "Prueba", TipoMovimiento.INGRESO, 10);
        Movimiento segundo = servicio.registrar("Dos", "Prueba", TipoMovimiento.GASTO, 5);

        assertEquals(1, primero.getId());
        assertEquals(2, segundo.getId());
    }

    @Test
    void rechazaDescripcionVacia() {
        assertThrows(IllegalArgumentException.class,
                () -> servicio.registrar("", "Trabajo", TipoMovimiento.INGRESO, 100));
    }

    @Test
    void rechazaMontoNegativo() {
        assertThrows(IllegalArgumentException.class,
                () -> servicio.registrar("Compra", "Casa", TipoMovimiento.GASTO, -10));
    }

    @Test
    void eliminaUnMovimiento() {
        Movimiento movimiento = servicio.registrar(
                "Compra", "Casa", TipoMovimiento.GASTO, 80);

        servicio.eliminar(movimiento.getId());

        assertEquals(0, servicio.listar().size());
    }

    private static class RepositorioDePrueba implements MovimientoRepositorio {
        private final List<Movimiento> movimientos = new ArrayList<>();

        @Override
        public List<Movimiento> listar() {
            return new ArrayList<>(movimientos);
        }

        @Override
        public void agregar(Movimiento movimiento) {
            movimientos.add(movimiento);
        }

        @Override
        public boolean eliminarPorId(int id) {
            return movimientos.removeIf(movimiento -> movimiento.getId() == id);
        }
    }
}

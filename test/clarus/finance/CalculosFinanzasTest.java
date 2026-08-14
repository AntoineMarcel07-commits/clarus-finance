package clarus.finance;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CalculosFinanzasTest {

    @Test
    public void sumaIngresos() {
        ArrayList<Movimiento> lista = new ArrayList<>();
        lista.add(crear("Ingreso", "Trabajo", 500));
        lista.add(crear("Ingreso", "Venta", 200));
        assertEquals(700, new CalculosFinanzas().totalIngresos(lista), 0.01);
    }

    @Test
    public void sumaGastos() {
        ArrayList<Movimiento> lista = new ArrayList<>();
        lista.add(crear("Gasto", "Comida", 100));
        lista.add(crear("Gasto", "Transporte", 20));
        assertEquals(120, new CalculosFinanzas().totalGastos(lista), 0.01);
    }

    @Test
    public void calculaSaldo() {
        ArrayList<Movimiento> lista = new ArrayList<>();
        lista.add(crear("Ingreso", "Trabajo", 500));
        lista.add(crear("Gasto", "Comida", 100));
        assertEquals(400, new CalculosFinanzas().saldo(lista), 0.01);
    }

    @Test
    public void sumaGastosPorCategoria() {
        ArrayList<Movimiento> lista = new ArrayList<>();
        lista.add(crear("Gasto", "Comida", 100));
        lista.add(crear("Gasto", "Comida", 50));
        lista.add(crear("Gasto", "Transporte", 20));
        assertEquals(150,
                new CalculosFinanzas().gastosPorCategoria(lista, "Comida"), 0.01);
    }

    @Test
    public void presupuestoDisponible() {
        assertEquals("Disponible",
                new CalculosFinanzas().estadoPresupuesto(1000, 500));
    }

    @Test
    public void presupuestoCercaDelLimite() {
        assertEquals("Cerca del límite",
                new CalculosFinanzas().estadoPresupuesto(1000, 850));
    }

    @Test
    public void presupuestoExcedido() {
        assertEquals("Excedido",
                new CalculosFinanzas().estadoPresupuesto(1000, 1100));
    }

    @Test
    public void creaMovimiento() {
        Movimiento movimiento = crear("Gasto", "Comida", 150);
        assertEquals("Comida", movimiento.categoria);
        assertEquals(150, movimiento.monto, 0.01);
    }

    @Test
    public void creaPresupuesto() {
        Presupuesto presupuesto = new Presupuesto()
                .InfoPresupuesto(1, "Comida", 1000);
        assertEquals("Comida", presupuesto.categoria);
        assertEquals(1000, presupuesto.limite, 0.01);
    }

    private Movimiento crear(String tipo, String categoria, double monto) {
        return new Movimiento().InfoMovimiento(
                1, "2026-01-10", tipo, categoria, "Prueba", monto);
    }
}

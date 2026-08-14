package clarus.finance;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculosFinanzasTest {

    @Test
    public void sumaIngresos() {
        ArrayList<Movimiento> lista = new ArrayList<>();
        lista.add(crear("Ingreso", "Trabajo", 500));
        lista.add(crear("Ingreso", "Venta", 200));
        CalculosFinanzas calculos = new CalculosFinanzas();
        assertEquals(700, calculos.totalIngresos(lista));
    }

    @Test
    public void sumaGastos() {
        ArrayList<Movimiento> lista = new ArrayList<>();
        lista.add(crear("Gasto", "Comida", 100));
        lista.add(crear("Gasto", "Transporte", 20));
        CalculosFinanzas calculos = new CalculosFinanzas();
        assertEquals(120, calculos.totalGastos(lista));
    }

    @Test
    public void calculaSaldo() {
        ArrayList<Movimiento> lista = new ArrayList<>();
        lista.add(crear("Ingreso", "Trabajo", 500));
        lista.add(crear("Gasto", "Comida", 100));
        CalculosFinanzas calculos = new CalculosFinanzas();
        assertEquals(400, calculos.saldo(lista));
    }

    @Test
    public void sumaGastosPorCategoria() {
        ArrayList<Movimiento> lista = new ArrayList<>();
        lista.add(crear("Gasto", "Comida", 100));
        lista.add(crear("Gasto", "Comida", 50));
        lista.add(crear("Gasto", "Transporte", 20));
        CalculosFinanzas calculos = new CalculosFinanzas();
        assertEquals(150, calculos.gastosPorCategoria(lista, "Comida"));
    }

    @Test
    public void presupuestoDisponible() {
        CalculosFinanzas calculos = new CalculosFinanzas();
        assertEquals("Disponible", calculos.estadoPresupuesto(1000, 500));
    }

    @Test
    public void presupuestoCercaDelLimite() {
        CalculosFinanzas calculos = new CalculosFinanzas();
        assertEquals("Cerca del límite", calculos.estadoPresupuesto(1000, 850));
    }

    @Test
    public void presupuestoExcedido() {
        CalculosFinanzas calculos = new CalculosFinanzas();
        assertEquals("Excedido", calculos.estadoPresupuesto(1000, 1100));
    }

    @Test
    public void creaMovimiento() {
        Movimiento movimiento = crear("Gasto", "Comida", 150);
        assertEquals("Comida", movimiento.categoria);
        assertEquals(150, movimiento.monto);
    }

    @Test
    public void creaPresupuesto() {
        Presupuesto objPresupuesto = new Presupuesto();
        Presupuesto presupuesto = objPresupuesto.InfoPresupuesto(1, "Comida", 1000);
        assertEquals("Comida", presupuesto.categoria);
        assertEquals(1000, presupuesto.limite);
    }

    private Movimiento crear(String tipo, String categoria, double monto) {
        Movimiento objMovimiento = new Movimiento();
        return objMovimiento.InfoMovimiento(
                1, "2026-08-14", tipo, categoria, "Prueba", monto);
    }
}

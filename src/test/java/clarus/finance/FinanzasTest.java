package clarus.finance;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FinanzasTest {

    @Test
    public void probarAgregarIngreso() {
        Finanzas objFinanzas = new Finanzas();
        objFinanzas.agregar("Pago", "Trabajo", "Ingreso", 500);
        assertEquals(1, objFinanzas.listaMovimientos.size());
    }

    @Test
    public void probarAgregarGasto() {
        Finanzas objFinanzas = new Finanzas();
        objFinanzas.agregar("Comida", "Casa", "Gasto", 100);
        assertEquals(1, objFinanzas.listaMovimientos.size());
    }

    @Test
    public void probarTotalIngresos() {
        Finanzas objFinanzas = new Finanzas();
        objFinanzas.agregar("Pago", "Trabajo", "Ingreso", 500);
        objFinanzas.agregar("Venta", "Otros", "Ingreso", 200);
        assertEquals(700, objFinanzas.totalIngresos());
    }

    @Test
    public void probarTotalGastos() {
        Finanzas objFinanzas = new Finanzas();
        objFinanzas.agregar("Comida", "Casa", "Gasto", 100);
        objFinanzas.agregar("Camion", "Transporte", "Gasto", 20);
        assertEquals(120, objFinanzas.totalGastos());
    }

    @Test
    public void probarSaldo() {
        Finanzas objFinanzas = new Finanzas();
        objFinanzas.agregar("Pago", "Trabajo", "Ingreso", 500);
        objFinanzas.agregar("Comida", "Casa", "Gasto", 100);
        assertEquals(400, objFinanzas.saldo());
    }

    @Test
    public void probarMontoIncorrecto() {
        Finanzas objFinanzas = new Finanzas();
        Movimiento resultado = objFinanzas.agregar("Compra", "Casa", "Gasto", -10);
        assertNull(resultado);
    }

    @Test
    public void probarEliminar() {
        Finanzas objFinanzas = new Finanzas();
        Movimiento movimiento = objFinanzas.agregar("Compra", "Casa", "Gasto", 80);
        objFinanzas.eliminar(movimiento.id);
        assertEquals(0, objFinanzas.listaMovimientos.size());
    }
}

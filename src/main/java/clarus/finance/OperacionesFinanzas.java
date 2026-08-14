package clarus.finance;

import java.util.ArrayList;

public interface OperacionesFinanzas {
    Movimiento agregar(String descripcion, String categoria, String tipo, double monto);

    boolean eliminar(int id);

    ArrayList<Movimiento> mostrarMovimientos();

    double totalIngresos();

    double totalGastos();

    double saldo();
}

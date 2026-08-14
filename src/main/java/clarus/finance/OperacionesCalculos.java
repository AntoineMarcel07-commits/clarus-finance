package clarus.finance;

import java.util.ArrayList;

public interface OperacionesCalculos {
    double totalIngresos(ArrayList<Movimiento> movimientos);

    double totalGastos(ArrayList<Movimiento> movimientos);

    double saldo(ArrayList<Movimiento> movimientos);

    double gastosPorCategoria(ArrayList<Movimiento> movimientos, String categoria);

    String estadoPresupuesto(double limite, double gastado);
}

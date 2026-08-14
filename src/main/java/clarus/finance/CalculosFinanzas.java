package clarus.finance;

import java.util.ArrayList;

public class CalculosFinanzas implements OperacionesCalculos {

    @Override
    public double totalIngresos(ArrayList<Movimiento> movimientos) {
        double total = 0;
        for (Movimiento movimiento : movimientos) {
            if (movimiento.tipo.equals("Ingreso")) {
                total = total + movimiento.monto;
            }
        }
        return total;
    }

    @Override
    public double totalGastos(ArrayList<Movimiento> movimientos) {
        double total = 0;
        for (Movimiento movimiento : movimientos) {
            if (movimiento.tipo.equals("Gasto")) {
                total = total + movimiento.monto;
            }
        }
        return total;
    }

    @Override
    public double saldo(ArrayList<Movimiento> movimientos) {
        return totalIngresos(movimientos) - totalGastos(movimientos);
    }

    @Override
    public double gastosPorCategoria(ArrayList<Movimiento> movimientos, String categoria) {
        double total = 0;
        for (Movimiento movimiento : movimientos) {
            if (movimiento.tipo.equals("Gasto")
                    && movimiento.categoria.equalsIgnoreCase(categoria)) {
                total = total + movimiento.monto;
            }
        }
        return total;
    }

    @Override
    public String estadoPresupuesto(double limite, double gastado) {
        if (gastado > limite) {
            return "Excedido";
        }
        if (gastado >= limite * 0.80) {
            return "Cerca del límite";
        }
        return "Disponible";
    }
}

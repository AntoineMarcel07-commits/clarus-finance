package clarus.finance;

import java.time.LocalDate;
import java.util.ArrayList;

public class Finanzas implements OperacionesFinanzas {
    public ArrayList<Movimiento> listaMovimientos = new ArrayList<>();

    @Override
    public Movimiento agregar(String descripcion, String categoria,
            String tipo, double monto) {
        if (descripcion.isBlank() || categoria.isBlank() || monto <= 0) {
            return null;
        }

        Movimiento objMovimiento = new Movimiento();
        Movimiento nuevo = objMovimiento.InfoMovimiento(
                siguienteId(),
                LocalDate.now().toString(),
                tipo,
                categoria,
                descripcion,
                monto);

        listaMovimientos.add(nuevo);
        return nuevo;
    }

    @Override
    public boolean eliminar(int id) {
        for (int i = 0; i < listaMovimientos.size(); i++) {
            if (listaMovimientos.get(i).id == id) {
                listaMovimientos.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public ArrayList<Movimiento> mostrarMovimientos() {
        return listaMovimientos;
    }

    @Override
    public double totalIngresos() {
        double total = 0;
        for (Movimiento movimiento : listaMovimientos) {
            if (movimiento.tipo.equals("Ingreso")) {
                total = total + movimiento.monto;
            }
        }
        return total;
    }

    @Override
    public double totalGastos() {
        double total = 0;
        for (Movimiento movimiento : listaMovimientos) {
            if (movimiento.tipo.equals("Gasto")) {
                total = total + movimiento.monto;
            }
        }
        return total;
    }

    @Override
    public double saldo() {
        return totalIngresos() - totalGastos();
    }

    private int siguienteId() {
        int idMayor = 0;
        for (Movimiento movimiento : listaMovimientos) {
            if (movimiento.id > idMayor) {
                idMayor = movimiento.id;
            }
        }
        return idMayor + 1;
    }
}

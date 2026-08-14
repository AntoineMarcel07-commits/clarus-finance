package com.clarusfinance.datos;

import com.clarusfinance.modelo.Movimiento;
import java.util.List;

public interface MovimientoRepositorio {
    List<Movimiento> listar();

    void agregar(Movimiento movimiento);

    boolean eliminarPorId(int id);
}

package com.clarusfinance.datos;

import com.clarusfinance.modelo.Movimiento;
import com.clarusfinance.modelo.TipoMovimiento;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArchivoMovimientoRepositorio implements MovimientoRepositorio {
    private final Path archivo;
    private final List<Movimiento> movimientos = new ArrayList<>();

    public ArchivoMovimientoRepositorio(Path archivo) {
        this.archivo = archivo;
        cargarArchivo();
    }

    @Override
    public List<Movimiento> listar() {
        return new ArrayList<>(movimientos);
    }

    @Override
    public void agregar(Movimiento movimiento) {
        movimientos.add(movimiento);
        guardarArchivo();
    }

    @Override
    public boolean eliminarPorId(int id) {
        boolean eliminado = movimientos.removeIf(movimiento -> movimiento.getId() == id);
        if (eliminado) {
            guardarArchivo();
        }
        return eliminado;
    }

    private void cargarArchivo() {
        if (!Files.exists(archivo)) {
            return;
        }

        try {
            for (String linea : Files.readAllLines(archivo, StandardCharsets.UTF_8)) {
                String[] datos = linea.split(";", 6);
                if (datos.length == 6) {
                    movimientos.add(new Movimiento(
                            Integer.parseInt(datos[0]),
                            LocalDate.parse(datos[1]),
                            TipoMovimiento.valueOf(datos[2]),
                            datos[3],
                            datos[4],
                            Double.parseDouble(datos[5])));
                }
            }
        } catch (IOException | RuntimeException error) {
            throw new IllegalStateException("No se pudo leer " + archivo + ".", error);
        }
    }

    private void guardarArchivo() {
        List<String> lineas = new ArrayList<>();
        for (Movimiento movimiento : movimientos) {
            lineas.add(movimiento.getId() + ";"
                    + movimiento.getFecha() + ";"
                    + movimiento.getTipo().name() + ";"
                    + limpiar(movimiento.getCategoria()) + ";"
                    + limpiar(movimiento.getDescripcion()) + ";"
                    + movimiento.getMonto());
        }

        try {
            if (archivo.getParent() != null) {
                Files.createDirectories(archivo.getParent());
            }
            Files.write(archivo, lineas, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException error) {
            throw new IllegalStateException("No se pudo guardar " + archivo + ".", error);
        }
    }

    private String limpiar(String texto) {
        return texto.replace(';', ',').replace('\n', ' ').replace('\r', ' ');
    }
}

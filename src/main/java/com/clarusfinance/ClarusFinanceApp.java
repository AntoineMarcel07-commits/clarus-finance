package com.clarusfinance;

import com.clarusfinance.datos.ArchivoMovimientoRepositorio;
import com.clarusfinance.datos.MovimientoRepositorio;
import com.clarusfinance.servicio.FinanzasServicio;
import com.clarusfinance.vista.VentanaPrincipal;
import java.nio.file.Path;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class ClarusFinanceApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Path archivo = Path.of("datos", "movimientos.csv");
                MovimientoRepositorio repositorio = new ArchivoMovimientoRepositorio(archivo);
                FinanzasServicio servicio = new FinanzasServicio(repositorio);

                VentanaPrincipal ventana = new VentanaPrincipal(servicio);
                ventana.setVisible(true);
            } catch (RuntimeException error) {
                JOptionPane.showMessageDialog(null,
                        error.getMessage(),
                        "No se pudo iniciar Clarus Finance",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

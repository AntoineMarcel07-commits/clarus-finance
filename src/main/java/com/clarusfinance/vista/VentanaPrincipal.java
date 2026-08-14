package com.clarusfinance.vista;

import com.clarusfinance.modelo.Movimiento;
import com.clarusfinance.modelo.TipoMovimiento;
import com.clarusfinance.servicio.FinanzasServicio;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VentanaPrincipal extends JFrame {
    private final FinanzasServicio servicio;
    private final JTextField descripcionCampo = new JTextField();
    private final JTextField categoriaCampo = new JTextField();
    private final JTextField montoCampo = new JTextField();
    private final JComboBox<TipoMovimiento> tipoCombo =
            new JComboBox<>(TipoMovimiento.values());
    private final JLabel ingresosEtiqueta = new JLabel();
    private final JLabel gastosEtiqueta = new JLabel();
    private final JLabel saldoEtiqueta = new JLabel();
    private final DefaultTableModel modeloTabla;
    private final JTable tabla;

    public VentanaPrincipal(FinanzasServicio servicio) {
        this.servicio = servicio;
        this.modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Fecha", "Tipo", "Categoría", "Descripción", "Monto"}, 0) {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        this.tabla = new JTable(modeloTabla);

        configurarVentana();
        construirPantalla();
        actualizarPantalla();
    }

    private void configurarVentana() {
        setTitle("Clarus Finance");
        setSize(850, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void construirPantalla() {
        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contenido.add(crearEncabezado(), BorderLayout.NORTH);
        contenido.add(new JScrollPane(tabla), BorderLayout.CENTER);
        contenido.add(crearFormulario(), BorderLayout.SOUTH);
        setContentPane(contenido);
    }

    private JPanel crearEncabezado() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Clarus Finance - Mis movimientos");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        panel.add(titulo, BorderLayout.NORTH);

        JPanel totales = new JPanel(new FlowLayout(FlowLayout.LEFT, 25, 8));
        totales.add(ingresosEtiqueta);
        totales.add(gastosEtiqueta);
        totales.add(saldoEtiqueta);
        panel.add(totales, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Nuevo movimiento"));

        JPanel campos = new JPanel(new GridLayout(2, 4, 8, 4));
        campos.add(new JLabel("Descripción"));
        campos.add(new JLabel("Categoría"));
        campos.add(new JLabel("Tipo"));
        campos.add(new JLabel("Monto"));
        campos.add(descripcionCampo);
        campos.add(categoriaCampo);
        campos.add(tipoCombo);
        campos.add(montoCampo);
        panel.add(campos, BorderLayout.CENTER);

        JButton agregarBoton = new JButton("Agregar");
        agregarBoton.addActionListener(evento -> agregarMovimiento());
        JButton eliminarBoton = new JButton("Eliminar seleccionado");
        eliminarBoton.addActionListener(evento -> eliminarMovimiento());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.add(eliminarBoton);
        botones.add(agregarBoton);
        panel.add(botones, BorderLayout.SOUTH);
        return panel;
    }

    private void agregarMovimiento() {
        try {
            double monto = Double.parseDouble(montoCampo.getText().trim().replace(',', '.'));
            TipoMovimiento tipo = (TipoMovimiento) tipoCombo.getSelectedItem();
            servicio.registrar(descripcionCampo.getText(), categoriaCampo.getText(), tipo, monto);
            descripcionCampo.setText("");
            categoriaCampo.setText("");
            montoCampo.setText("");
            actualizarPantalla();
        } catch (NumberFormatException error) {
            mostrarError("Escribe un monto válido, por ejemplo 150.50.");
        } catch (IllegalArgumentException error) {
            mostrarError(error.getMessage());
        }
    }

    private void eliminarMovimiento() {
        int fila = tabla.getSelectedRow();
        if (fila == -1) {
            mostrarError("Selecciona una fila de la tabla.");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el movimiento seleccionado?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            int id = (int) modeloTabla.getValueAt(fila, 0);
            servicio.eliminar(id);
            actualizarPantalla();
        }
    }

    private void actualizarPantalla() {
        modeloTabla.setRowCount(0);
        List<Movimiento> movimientos = servicio.listar();
        for (int i = movimientos.size() - 1; i >= 0; i--) {
            Movimiento movimiento = movimientos.get(i);
            modeloTabla.addRow(new Object[]{
                movimiento.getId(), movimiento.getFecha(), movimiento.getTipo(),
                movimiento.getCategoria(), movimiento.getDescripcion(),
                String.format("$ %.2f", movimiento.getMonto())
            });
        }

        ingresosEtiqueta.setText(String.format("Ingresos: $ %.2f", servicio.totalIngresos()));
        gastosEtiqueta.setText(String.format("Gastos: $ %.2f", servicio.totalGastos()));
        saldoEtiqueta.setText(String.format("Saldo: $ %.2f", servicio.saldo()));
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Revisa los datos",
                JOptionPane.WARNING_MESSAGE);
    }
}

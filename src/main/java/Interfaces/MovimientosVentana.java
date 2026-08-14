package Interfaces;

import clarus.finance.Movimiento;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
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

import static clarus.finance.ClarusFinance.objConnection;
import static clarus.finance.ClarusFinance.objMovimiento;

public class MovimientosVentana extends JFrame {
    JTextField txtDescripcion;
    JTextField txtCategoria;
    JTextField txtMonto;
    JTextField txtFecha;
    JComboBox<String> cmbTipo;
    JTable tablaMovimientos;
    DefaultTableModel modeloTabla;
    int idSeleccionado = 0;

    public MovimientosVentana() {
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        setTitle("Clarus Finance - Movimientos");
        setSize(900, 570);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("INGRESOS Y GASTOS", JLabel.CENTER);

        txtDescripcion = new JTextField();
        txtCategoria = new JTextField();
        txtMonto = new JTextField();
        txtFecha = new JTextField(LocalDate.now().toString());
        cmbTipo = new JComboBox<>(new String[]{"Ingreso", "Gasto"});

        JPanel formulario = new JPanel(new GridLayout(5, 2, 6, 6));
        formulario.add(new JLabel("Descripción:"));
        formulario.add(txtDescripcion);
        formulario.add(new JLabel("Categoría:"));
        formulario.add(txtCategoria);
        formulario.add(new JLabel("Tipo:"));
        formulario.add(cmbTipo);
        formulario.add(new JLabel("Monto:"));
        formulario.add(txtMonto);
        formulario.add(new JLabel("Fecha (AAAA-MM-DD):"));
        formulario.add(txtFecha);

        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Fecha", "Tipo", "Categoría", "Descripción", "Monto"}, 0);
        tablaMovimientos = new JTable(modeloTabla);
        tablaMovimientos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                seleccionarFila();
            }
        });

        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);
        JButton btnActualizar = new JButton("ACTUALIZAR");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);
        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);
        JButton btnLimpiar = new JButton("LIMPIAR");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);
        JButton btnVolver = new JButton("VOLVER");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        JPanel botones = new JPanel();
        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);
        botones.add(btnVolver);

        JPanel izquierda = new JPanel(new BorderLayout(8, 8));
        izquierda.add(formulario, BorderLayout.NORTH);
        izquierda.add(botones, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(titulo, BorderLayout.NORTH);
        add(izquierda, BorderLayout.WEST);
        add(new JScrollPane(tablaMovimientos), BorderLayout.CENTER);
    }

    private Movimiento leerFormulario(int id) {
        String descripcion = txtDescripcion.getText().trim();
        String categoria = txtCategoria.getText().trim();
        String tipo = cmbTipo.getSelectedItem().toString();
        String fecha = txtFecha.getText().trim();

        if (descripcion.isEmpty() || categoria.isEmpty() || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa todos los campos");
            return null;
        }

        try {
            double monto = Double.parseDouble(txtMonto.getText());
            LocalDate.parse(fecha);
            if (monto <= 0) {
                JOptionPane.showMessageDialog(this, "El monto debe ser mayor a cero");
                return null;
            }
            return objMovimiento.InfoMovimiento(
                    id, fecha, tipo, categoria, descripcion, monto);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Revisa el monto y la fecha");
            return null;
        }
    }

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {
        Movimiento movimiento = leerFormulario(0);
        if (movimiento != null && objMovimiento.insertar(objConnection, movimiento)) {
            JOptionPane.showMessageDialog(this, "Movimiento agregado");
            limpiarFormulario();
            cargarTabla();
        }
    }

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila");
            return;
        }

        Movimiento movimiento = leerFormulario(idSeleccionado);
        if (movimiento != null && objMovimiento.actualizar(objConnection, movimiento)) {
            JOptionPane.showMessageDialog(this, "Movimiento actualizado");
            limpiarFormulario();
            cargarTabla();
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila");
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el movimiento?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION
                && objMovimiento.eliminar(objConnection, idSeleccionado)) {
            limpiarFormulario();
            cargarTabla();
        }
    }

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {
        limpiarFormulario();
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        new MenuPrincipal().setVisible(true);
        dispose();
    }

    private void seleccionarFila() {
        int fila = tablaMovimientos.getSelectedRow();
        if (fila >= 0) {
            idSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
            txtFecha.setText(modeloTabla.getValueAt(fila, 1).toString());
            cmbTipo.setSelectedItem(modeloTabla.getValueAt(fila, 2).toString());
            txtCategoria.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtDescripcion.setText(modeloTabla.getValueAt(fila, 4).toString());
            txtMonto.setText(modeloTabla.getValueAt(fila, 5).toString());
        }
    }

    private void limpiarFormulario() {
        idSeleccionado = 0;
        txtDescripcion.setText("");
        txtCategoria.setText("");
        txtMonto.setText("");
        txtFecha.setText(LocalDate.now().toString());
        tablaMovimientos.clearSelection();
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        ArrayList<Movimiento> lista = objMovimiento.listar(objConnection);
        for (Movimiento movimiento : lista) {
            modeloTabla.addRow(new Object[]{
                movimiento.id,
                movimiento.fecha,
                movimiento.tipo,
                movimiento.categoria,
                movimiento.descripcion,
                movimiento.monto
            });
        }
    }
}

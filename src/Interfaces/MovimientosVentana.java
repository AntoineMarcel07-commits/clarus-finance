package Interfaces;

import clarus.finance.ClarusFinance;
import clarus.finance.Movimiento;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MovimientosVentana extends JFrame {

    private DefaultTableModel modelo;
    private int idSeleccionado;

    public MovimientosVentana() {
        initComponents();
        setSize(960, 650);
        setLocationRelativeTo(null);
        modelo = (DefaultTableModel) tblMovimientos.getModel();
        txtFecha.setText(LocalDate.now().toString());
        tblMovimientos.getSelectionModel()
                .addListSelectionListener(e -> seleccionarFila());
        cargarTabla();
    }

    /**
     * Código creado por el diseñador visual de NetBeans.
     * Para mover controles usa la pestaña Design.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblFecha = new javax.swing.JLabel();
        txtFecha = new javax.swing.JTextField();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        lblCategoria = new javax.swing.JLabel();
        txtCategoria = new javax.swing.JTextField();
        lblDescripcion = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        lblMonto = new javax.swing.JLabel();
        txtMonto = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        scrollMovimientos = new javax.swing.JScrollPane();
        tblMovimientos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Clarus Finance - Movimientos");
        setResizable(false);
        getContentPane().setBackground(new java.awt.Color(245, 247, 250));
        getContentPane().setLayout(null);

        lblTitulo.setFont(new java.awt.Font("SansSerif", 1, 28));
        lblTitulo.setForeground(new java.awt.Color(32, 68, 115));
        lblTitulo.setText("Movimientos");
        getContentPane().add(lblTitulo);
        lblTitulo.setBounds(30, 20, 260, 40);

        lblFecha.setFont(new java.awt.Font("SansSerif", 1, 13));
        lblFecha.setText("Fecha (AAAA-MM-DD)");
        getContentPane().add(lblFecha);
        lblFecha.setBounds(30, 80, 170, 20);
        getContentPane().add(txtFecha);
        txtFecha.setBounds(30, 105, 170, 35);

        lblTipo.setFont(new java.awt.Font("SansSerif", 1, 13));
        lblTipo.setText("Tipo");
        getContentPane().add(lblTipo);
        lblTipo.setBounds(220, 80, 100, 20);
        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{"Ingreso", "Gasto"}));
        getContentPane().add(cmbTipo);
        cmbTipo.setBounds(220, 105, 150, 35);

        lblCategoria.setFont(new java.awt.Font("SansSerif", 1, 13));
        lblCategoria.setText("Categoría");
        getContentPane().add(lblCategoria);
        lblCategoria.setBounds(390, 80, 120, 20);
        getContentPane().add(txtCategoria);
        txtCategoria.setBounds(390, 105, 190, 35);

        lblDescripcion.setFont(new java.awt.Font("SansSerif", 1, 13));
        lblDescripcion.setText("Descripción");
        getContentPane().add(lblDescripcion);
        lblDescripcion.setBounds(30, 155, 120, 20);
        getContentPane().add(txtDescripcion);
        txtDescripcion.setBounds(30, 180, 550, 35);

        lblMonto.setFont(new java.awt.Font("SansSerif", 1, 13));
        lblMonto.setText("Monto");
        getContentPane().add(lblMonto);
        lblMonto.setBounds(600, 80, 100, 20);
        getContentPane().add(txtMonto);
        txtMonto.setBounds(600, 105, 150, 35);

        btnGuardar.setBackground(new java.awt.Color(32, 68, 115));
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar");
        btnGuardar.setBorderPainted(false);
        btnGuardar.setOpaque(true);
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);
        getContentPane().add(btnGuardar);
        btnGuardar.setBounds(610, 175, 140, 40);

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);
        getContentPane().add(btnActualizar);
        btnActualizar.setBounds(770, 100, 140, 38);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);
        getContentPane().add(btnEliminar);
        btnEliminar.setBounds(770, 150, 140, 38);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);
        getContentPane().add(btnLimpiar);
        btnLimpiar.setBounds(770, 200, 140, 38);

        tblMovimientos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Fecha", "Tipo", "Categoría", "Descripción", "Monto"}
        ) {
            boolean[] canEdit = new boolean [] {false, false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tblMovimientos.setRowHeight(27);
        scrollMovimientos.setViewportView(tblMovimientos);
        getContentPane().add(scrollMovimientos);
        scrollMovimientos.setBounds(30, 265, 880, 320);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private Movimiento leerFormulario(int id) {
        String fecha = txtFecha.getText().trim();
        String tipo = cmbTipo.getSelectedItem().toString();
        String categoria = txtCategoria.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        double monto = Double.parseDouble(txtMonto.getText().trim());

        if (categoria.isEmpty() || descripcion.isEmpty() || monto <= 0) {
            throw new IllegalArgumentException();
        }
        LocalDate.parse(fecha);
        return new Movimiento().InfoMovimiento(
                id, fecha, tipo, categoria, descripcion, monto);
    }

    private void btnGuardarActionPerformed(ActionEvent evt) {
        try {
            Movimiento movimiento = leerFormulario(0);
            if (new Movimiento().insertar(ClarusFinance.objConnection, movimiento)) {
                JOptionPane.showMessageDialog(this, "Movimiento guardado");
                limpiar();
                cargarTabla();
                actualizarMenu();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Completa los datos, usa una fecha válida y un monto mayor a cero");
        }
    }

    private void btnActualizarActionPerformed(ActionEvent evt) {
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un movimiento de la tabla");
            return;
        }
        try {
            Movimiento movimiento = leerFormulario(idSeleccionado);
            if (new Movimiento().actualizar(ClarusFinance.objConnection, movimiento)) {
                JOptionPane.showMessageDialog(this, "Movimiento actualizado");
                limpiar();
                cargarTabla();
                actualizarMenu();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Revisa los datos del movimiento");
        }
    }

    private void btnEliminarActionPerformed(ActionEvent evt) {
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un movimiento de la tabla");
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(
                this, "¿Eliminar el movimiento seleccionado?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION
                && new Movimiento().eliminar(ClarusFinance.objConnection, idSeleccionado)) {
            limpiar();
            cargarTabla();
            actualizarMenu();
        }
    }

    private void btnLimpiarActionPerformed(ActionEvent evt) {
        limpiar();
    }

    private void seleccionarFila() {
        int fila = tblMovimientos.getSelectedRow();
        if (fila >= 0) {
            idSeleccionado = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
            txtFecha.setText(modelo.getValueAt(fila, 1).toString());
            cmbTipo.setSelectedItem(modelo.getValueAt(fila, 2).toString());
            txtCategoria.setText(modelo.getValueAt(fila, 3).toString());
            txtDescripcion.setText(modelo.getValueAt(fila, 4).toString());
            txtMonto.setText(modelo.getValueAt(fila, 5).toString());
        }
    }

    private void limpiar() {
        idSeleccionado = 0;
        txtFecha.setText(LocalDate.now().toString());
        cmbTipo.setSelectedIndex(0);
        txtCategoria.setText("");
        txtDescripcion.setText("");
        txtMonto.setText("");
        tblMovimientos.clearSelection();
    }

    private void cargarTabla() {
        new Movimiento().cargarTabla(ClarusFinance.objConnection, modelo);
    }

    private void actualizarMenu() {
        if (ClarusFinance.objMenuPrincipal != null) {
            ClarusFinance.objMenuPrincipal.actualizarDatos();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JComboBox<String> cmbTipo;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblMonto;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JScrollPane scrollMovimientos;
    private javax.swing.JTable tblMovimientos;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JTextField txtMonto;
    // End of variables declaration//GEN-END:variables
}

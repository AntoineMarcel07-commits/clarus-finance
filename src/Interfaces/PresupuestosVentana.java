package Interfaces;

import clarus.finance.CalculosFinanzas;
import clarus.finance.ClarusFinance;
import clarus.finance.Movimiento;
import clarus.finance.OperacionesCalculos;
import clarus.finance.Presupuesto;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class PresupuestosVentana extends JFrame {

    private DefaultTableModel modelo;
    private int idSeleccionado;

    public PresupuestosVentana() {
        initComponents();
        setSize(780, 540);
        setLocationRelativeTo(null);
        modelo = (DefaultTableModel) tblPresupuestos.getModel();
        tblPresupuestos.getSelectionModel()
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
        lblCategoria = new javax.swing.JLabel();
        txtCategoria = new javax.swing.JTextField();
        lblLimite = new javax.swing.JLabel();
        txtLimite = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        scrollPresupuestos = new javax.swing.JScrollPane();
        tblPresupuestos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Clarus Finance - Presupuestos");
        setResizable(false);
        getContentPane().setBackground(new java.awt.Color(245, 247, 250));
        getContentPane().setLayout(null);

        lblTitulo.setFont(new java.awt.Font("SansSerif", 1, 28));
        lblTitulo.setForeground(new java.awt.Color(32, 68, 115));
        lblTitulo.setText("Presupuestos");
        getContentPane().add(lblTitulo);
        lblTitulo.setBounds(30, 20, 260, 40);

        lblCategoria.setFont(new java.awt.Font("SansSerif", 1, 13));
        lblCategoria.setText("Categoría");
        getContentPane().add(lblCategoria);
        lblCategoria.setBounds(30, 85, 120, 20);
        getContentPane().add(txtCategoria);
        txtCategoria.setBounds(30, 110, 240, 36);

        lblLimite.setFont(new java.awt.Font("SansSerif", 1, 13));
        lblLimite.setText("Límite");
        getContentPane().add(lblLimite);
        lblLimite.setBounds(290, 85, 100, 20);
        getContentPane().add(txtLimite);
        txtLimite.setBounds(290, 110, 150, 36);

        btnGuardar.setBackground(new java.awt.Color(32, 68, 115));
        btnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        btnGuardar.setText("Guardar / actualizar");
        btnGuardar.setBorderPainted(false);
        btnGuardar.setOpaque(true);
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);
        getContentPane().add(btnGuardar);
        btnGuardar.setBounds(465, 107, 170, 40);

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);
        getContentPane().add(btnEliminar);
        btnEliminar.setBounds(30, 170, 140, 38);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);
        getContentPane().add(btnLimpiar);
        btnLimpiar.setBounds(190, 170, 140, 38);

        tblPresupuestos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {},
            new String [] {"ID", "Categoría", "Límite", "Gastado", "Estado"}
        ) {
            boolean[] canEdit = new boolean [] {false, false, false, false, false};
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        tblPresupuestos.setRowHeight(27);
        scrollPresupuestos.setViewportView(tblPresupuestos);
        getContentPane().add(scrollPresupuestos);
        scrollPresupuestos.setBounds(30, 235, 710, 230);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(ActionEvent evt) {
        try {
            String categoria = txtCategoria.getText().trim();
            double limite = Double.parseDouble(txtLimite.getText().trim());
            if (categoria.isEmpty() || limite <= 0) {
                throw new IllegalArgumentException();
            }
            Presupuesto presupuesto = new Presupuesto()
                    .InfoPresupuesto(idSeleccionado, categoria, limite);
            if (new Presupuesto().guardar(ClarusFinance.objConnection, presupuesto)) {
                JOptionPane.showMessageDialog(this, "Presupuesto guardado");
                limpiar();
                cargarTabla();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Escribe una categoría y un límite mayor a cero");
        }
    }

    private void btnEliminarActionPerformed(ActionEvent evt) {
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un presupuesto de la tabla");
            return;
        }
        int respuesta = JOptionPane.showConfirmDialog(
                this, "¿Eliminar el presupuesto seleccionado?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION
                && new Presupuesto().eliminar(ClarusFinance.objConnection, idSeleccionado)) {
            limpiar();
            cargarTabla();
        }
    }

    private void btnLimpiarActionPerformed(ActionEvent evt) {
        limpiar();
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        ArrayList<Movimiento> movimientos = new Movimiento()
                .listar(ClarusFinance.objConnection);
        OperacionesCalculos calculos = new CalculosFinanzas();

        for (Presupuesto presupuesto : new Presupuesto()
                .listar(ClarusFinance.objConnection)) {
            double gastado = calculos.gastosPorCategoria(
                    movimientos, presupuesto.categoria);
            modelo.addRow(new Object[]{
                presupuesto.id,
                presupuesto.categoria,
                presupuesto.limite,
                gastado,
                calculos.estadoPresupuesto(presupuesto.limite, gastado)
            });
        }
    }

    private void seleccionarFila() {
        int fila = tblPresupuestos.getSelectedRow();
        if (fila >= 0) {
            idSeleccionado = Integer.parseInt(modelo.getValueAt(fila, 0).toString());
            txtCategoria.setText(modelo.getValueAt(fila, 1).toString());
            txtLimite.setText(modelo.getValueAt(fila, 2).toString());
        }
    }

    private void limpiar() {
        idSeleccionado = 0;
        txtCategoria.setText("");
        txtLimite.setText("");
        tblPresupuestos.clearSelection();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblLimite;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JScrollPane scrollPresupuestos;
    private javax.swing.JTable tblPresupuestos;
    private javax.swing.JTextField txtCategoria;
    private javax.swing.JTextField txtLimite;
    // End of variables declaration//GEN-END:variables
}

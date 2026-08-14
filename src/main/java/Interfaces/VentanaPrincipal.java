package Interfaces;

import clarus.finance.Movimiento;
import clarus.finance.OperacionesFinanzas;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

public class VentanaPrincipal extends JFrame {
    OperacionesFinanzas objFinanzas;
    JTextField txtDescripcion;
    JTextField txtCategoria;
    JTextField txtMonto;
    JComboBox<String> cmbTipo;
    JTable tablaMovimientos;
    DefaultTableModel modeloTabla;
    JLabel lblIngresos;
    JLabel lblGastos;
    JLabel lblSaldo;

    public VentanaPrincipal(OperacionesFinanzas objFinanzas) {
        this.objFinanzas = objFinanzas;
        initComponents();
        actualizarTabla();
    }

    private void initComponents() {
        setTitle("Clarus Finance");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("CLARUS FINANCE - INGRESOS Y GASTOS");
        JPanel panelTitulo = new JPanel();
        panelTitulo.add(titulo);

        lblIngresos = new JLabel("Ingresos: $0.00");
        lblGastos = new JLabel("Gastos: $0.00");
        lblSaldo = new JLabel("Saldo: $0.00");
        JPanel panelTotales = new JPanel(new FlowLayout());
        panelTotales.add(lblIngresos);
        panelTotales.add(lblGastos);
        panelTotales.add(lblSaldo);

        JPanel panelArriba = new JPanel(new BorderLayout());
        panelArriba.add(panelTitulo, BorderLayout.NORTH);
        panelArriba.add(panelTotales, BorderLayout.SOUTH);

        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Fecha", "Tipo", "Categoria", "Descripcion", "Monto"}, 0);
        tablaMovimientos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaMovimientos);

        txtDescripcion = new JTextField();
        txtCategoria = new JTextField();
        txtMonto = new JTextField();
        cmbTipo = new JComboBox<>(new String[]{"Ingreso", "Gasto"});

        JPanel panelCampos = new JPanel(new GridLayout(2, 4, 5, 5));
        panelCampos.add(new JLabel("Descripcion"));
        panelCampos.add(new JLabel("Categoria"));
        panelCampos.add(new JLabel("Tipo"));
        panelCampos.add(new JLabel("Monto"));
        panelCampos.add(txtDescripcion);
        panelCampos.add(txtCategoria);
        panelCampos.add(cmbTipo);
        panelCampos.add(txtMonto);

        JButton btnAgregar = new JButton("AGREGAR");
        btnAgregar.addActionListener(this::btnAgregarActionPerformed);
        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);

        JPanel panelAbajo = new JPanel(new BorderLayout());
        panelAbajo.add(panelCampos, BorderLayout.CENTER);
        panelAbajo.add(panelBotones, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(panelArriba, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelAbajo, BorderLayout.SOUTH);
    }

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            String descripcion = txtDescripcion.getText();
            String categoria = txtCategoria.getText();
            String tipo = cmbTipo.getSelectedItem().toString();
            double monto = Double.parseDouble(txtMonto.getText());

            Movimiento movimiento = objFinanzas.agregar(descripcion, categoria, tipo, monto);
            if (movimiento == null) {
                JOptionPane.showMessageDialog(this, "Completa los datos correctamente");
            } else {
                txtDescripcion.setText("");
                txtCategoria.setText("");
                txtMonto.setText("");
                actualizarTabla();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El monto debe ser un numero");
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        int fila = tablaMovimientos.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un movimiento");
            return;
        }

        int id = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
        objFinanzas.eliminar(id);
        actualizarTabla();
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        ArrayList<Movimiento> lista = objFinanzas.mostrarMovimientos();

        for (Movimiento movimiento : lista) {
            modeloTabla.addRow(new Object[]{
                movimiento.id,
                movimiento.fecha,
                movimiento.tipo,
                movimiento.categoria,
                movimiento.descripcion,
                "$" + movimiento.monto
            });
        }

        lblIngresos.setText("Ingresos: $" + objFinanzas.totalIngresos());
        lblGastos.setText("Gastos: $" + objFinanzas.totalGastos());
        lblSaldo.setText("Saldo: $" + objFinanzas.saldo());
    }
}

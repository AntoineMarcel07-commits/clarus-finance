package Interfaces;

import clarus.finance.Presupuesto;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import static clarus.finance.ClarusFinance.objConnection;
import static clarus.finance.ClarusFinance.objPresupuesto;

public class PresupuestosVentana extends JFrame {
    JTextField txtCategoria;
    JTextField txtLimite;
    JTable tablaPresupuestos;
    DefaultTableModel modeloTabla;
    int idSeleccionado = 0;

    public PresupuestosVentana() {
        initComponents();
        cargarTabla();
    }

    private void initComponents() {
        setTitle("Clarus Finance - Presupuestos");
        setSize(680, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("PRESUPUESTOS POR CATEGORÍA", JLabel.CENTER);
        txtCategoria = new JTextField();
        txtLimite = new JTextField();

        JPanel formulario = new JPanel(new GridLayout(2, 2, 6, 6));
        formulario.add(new JLabel("Categoría:"));
        formulario.add(txtCategoria);
        formulario.add(new JLabel("Límite:"));
        formulario.add(txtLimite);

        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Categoría", "Límite"}, 0);
        tablaPresupuestos = new JTable(modeloTabla);
        tablaPresupuestos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                seleccionarFila();
            }
        });

        JButton btnGuardar = new JButton("GUARDAR / ACTUALIZAR");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);
        JButton btnEliminar = new JButton("ELIMINAR");
        btnEliminar.addActionListener(this::btnEliminarActionPerformed);
        JButton btnLimpiar = new JButton("LIMPIAR");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);
        JButton btnVolver = new JButton("VOLVER");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        JPanel botones = new JPanel();
        botones.add(btnGuardar);
        botones.add(btnEliminar);
        botones.add(btnLimpiar);
        botones.add(btnVolver);

        JPanel arriba = new JPanel(new BorderLayout(8, 8));
        arriba.add(titulo, BorderLayout.NORTH);
        arriba.add(formulario, BorderLayout.CENTER);

        setLayout(new BorderLayout(10, 10));
        add(arriba, BorderLayout.NORTH);
        add(new JScrollPane(tablaPresupuestos), BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {
        String categoria = txtCategoria.getText().trim();
        if (categoria.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribe una categoría");
            return;
        }

        try {
            double limite = Double.parseDouble(txtLimite.getText());
            if (limite <= 0) {
                JOptionPane.showMessageDialog(this, "El límite debe ser mayor a cero");
                return;
            }

            Presupuesto presupuesto = objPresupuesto.InfoPresupuesto(
                    idSeleccionado, categoria, limite);
            if (objPresupuesto.guardar(objConnection, presupuesto)) {
                JOptionPane.showMessageDialog(this, "Presupuesto guardado");
                limpiarFormulario();
                cargarTabla();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El límite debe ser un número");
        }
    }

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {
        if (idSeleccionado == 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un presupuesto");
            return;
        }

        if (objPresupuesto.eliminar(objConnection, idSeleccionado)) {
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
        int fila = tablaPresupuestos.getSelectedRow();
        if (fila >= 0) {
            idSeleccionado = Integer.parseInt(modeloTabla.getValueAt(fila, 0).toString());
            txtCategoria.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtLimite.setText(modeloTabla.getValueAt(fila, 2).toString());
        }
    }

    private void limpiarFormulario() {
        idSeleccionado = 0;
        txtCategoria.setText("");
        txtLimite.setText("");
        tablaPresupuestos.clearSelection();
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        ArrayList<Presupuesto> lista = objPresupuesto.listar(objConnection);
        for (Presupuesto presupuesto : lista) {
            modeloTabla.addRow(new Object[]{
                presupuesto.id,
                presupuesto.categoria,
                presupuesto.limite
            });
        }
    }
}

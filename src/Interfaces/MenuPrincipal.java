package Interfaces;

import clarus.finance.CalculosFinanzas;
import clarus.finance.ClarusFinance;
import clarus.finance.Movimiento;
import clarus.finance.OperacionesCalculos;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JFrame;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        initComponents();
        setSize(700, 460);
        setLocationRelativeTo(null);
        actualizarDatos();
    }

    /**
     * Código creado por el diseñador visual de NetBeans.
     * Para mover controles usa la pestaña Design.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblBienvenida = new javax.swing.JLabel();
        panelIngresos = new javax.swing.JPanel();
        lblTextoIngresos = new javax.swing.JLabel();
        lblIngresos = new javax.swing.JLabel();
        panelGastos = new javax.swing.JPanel();
        lblTextoGastos = new javax.swing.JLabel();
        lblGastos = new javax.swing.JLabel();
        panelSaldo = new javax.swing.JPanel();
        lblTextoSaldo = new javax.swing.JLabel();
        lblSaldo = new javax.swing.JLabel();
        btnMovimientos = new javax.swing.JButton();
        btnPresupuestos = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Clarus Finance - Menú principal");
        setResizable(false);
        getContentPane().setBackground(new java.awt.Color(245, 247, 250));
        getContentPane().setLayout(null);

        lblTitulo.setFont(new java.awt.Font("SansSerif", 1, 28));
        lblTitulo.setForeground(new java.awt.Color(32, 68, 115));
        lblTitulo.setText("Clarus Finance");
        getContentPane().add(lblTitulo);
        lblTitulo.setBounds(30, 25, 250, 40);

        lblBienvenida.setFont(new java.awt.Font("SansSerif", 0, 15));
        lblBienvenida.setText("Resumen de tus finanzas personales");
        getContentPane().add(lblBienvenida);
        lblBienvenida.setBounds(30, 65, 300, 25);

        panelIngresos.setBackground(new java.awt.Color(221, 242, 229));
        panelIngresos.setLayout(null);
        lblTextoIngresos.setFont(new java.awt.Font("SansSerif", 1, 14));
        lblTextoIngresos.setText("INGRESOS");
        panelIngresos.add(lblTextoIngresos);
        lblTextoIngresos.setBounds(18, 15, 120, 22);
        lblIngresos.setFont(new java.awt.Font("SansSerif", 1, 22));
        lblIngresos.setText("$0.00");
        panelIngresos.add(lblIngresos);
        lblIngresos.setBounds(18, 50, 170, 35);
        getContentPane().add(panelIngresos);
        panelIngresos.setBounds(30, 115, 195, 105);

        panelGastos.setBackground(new java.awt.Color(250, 226, 226));
        panelGastos.setLayout(null);
        lblTextoGastos.setFont(new java.awt.Font("SansSerif", 1, 14));
        lblTextoGastos.setText("GASTOS");
        panelGastos.add(lblTextoGastos);
        lblTextoGastos.setBounds(18, 15, 120, 22);
        lblGastos.setFont(new java.awt.Font("SansSerif", 1, 22));
        lblGastos.setText("$0.00");
        panelGastos.add(lblGastos);
        lblGastos.setBounds(18, 50, 170, 35);
        getContentPane().add(panelGastos);
        panelGastos.setBounds(250, 115, 195, 105);

        panelSaldo.setBackground(new java.awt.Color(222, 234, 248));
        panelSaldo.setLayout(null);
        lblTextoSaldo.setFont(new java.awt.Font("SansSerif", 1, 14));
        lblTextoSaldo.setText("SALDO");
        panelSaldo.add(lblTextoSaldo);
        lblTextoSaldo.setBounds(18, 15, 120, 22);
        lblSaldo.setFont(new java.awt.Font("SansSerif", 1, 22));
        lblSaldo.setText("$0.00");
        panelSaldo.add(lblSaldo);
        lblSaldo.setBounds(18, 50, 170, 35);
        getContentPane().add(panelSaldo);
        panelSaldo.setBounds(470, 115, 195, 105);

        btnMovimientos.setBackground(new java.awt.Color(32, 68, 115));
        btnMovimientos.setFont(new java.awt.Font("SansSerif", 1, 14));
        btnMovimientos.setForeground(new java.awt.Color(255, 255, 255));
        btnMovimientos.setText("Movimientos");
        btnMovimientos.setBorderPainted(false);
        btnMovimientos.setOpaque(true);
        btnMovimientos.addActionListener(this::btnMovimientosActionPerformed);
        getContentPane().add(btnMovimientos);
        btnMovimientos.setBounds(90, 270, 220, 48);

        btnPresupuestos.setBackground(new java.awt.Color(32, 68, 115));
        btnPresupuestos.setFont(new java.awt.Font("SansSerif", 1, 14));
        btnPresupuestos.setForeground(new java.awt.Color(255, 255, 255));
        btnPresupuestos.setText("Presupuestos");
        btnPresupuestos.setBorderPainted(false);
        btnPresupuestos.setOpaque(true);
        btnPresupuestos.addActionListener(this::btnPresupuestosActionPerformed);
        getContentPane().add(btnPresupuestos);
        btnPresupuestos.setBounds(390, 270, 220, 48);

        btnActualizar.setText("Actualizar resumen");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);
        getContentPane().add(btnActualizar);
        btnActualizar.setBounds(90, 345, 220, 42);

        btnCerrarSesion.setText("Cerrar sesión");
        btnCerrarSesion.addActionListener(this::btnCerrarSesionActionPerformed);
        getContentPane().add(btnCerrarSesion);
        btnCerrarSesion.setBounds(390, 345, 220, 42);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void actualizarDatos() {
        ArrayList<Movimiento> movimientos = new Movimiento()
                .listar(ClarusFinance.objConnection);
        OperacionesCalculos calculos = new CalculosFinanzas();
        lblIngresos.setText(String.format("$%.2f", calculos.totalIngresos(movimientos)));
        lblGastos.setText(String.format("$%.2f", calculos.totalGastos(movimientos)));
        lblSaldo.setText(String.format("$%.2f", calculos.saldo(movimientos)));
    }

    private void btnMovimientosActionPerformed(ActionEvent evt) {
        new MovimientosVentana().setVisible(true);
    }

    private void btnPresupuestosActionPerformed(ActionEvent evt) {
        new PresupuestosVentana().setVisible(true);
    }

    private void btnActualizarActionPerformed(ActionEvent evt) {
        actualizarDatos();
    }

    private void btnCerrarSesionActionPerformed(ActionEvent evt) {
        ClarusFinance.cerrarSesion();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnMovimientos;
    private javax.swing.JButton btnPresupuestos;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblGastos;
    private javax.swing.JLabel lblIngresos;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JLabel lblTextoGastos;
    private javax.swing.JLabel lblTextoIngresos;
    private javax.swing.JLabel lblTextoSaldo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JPanel panelGastos;
    private javax.swing.JPanel panelIngresos;
    private javax.swing.JPanel panelSaldo;
    // End of variables declaration//GEN-END:variables
}

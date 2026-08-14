package Interfaces;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Clarus Finance - Menú");
        setSize(430, 310);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("MENÚ PRINCIPAL", JLabel.CENTER);
        JLabel mensaje = new JLabel("Selecciona una opción", JLabel.CENTER);

        JButton btnDashboard = new JButton("DASHBOARD");
        btnDashboard.addActionListener(this::btnDashboardActionPerformed);
        JButton btnMovimientos = new JButton("MOVIMIENTOS");
        btnMovimientos.addActionListener(this::btnMovimientosActionPerformed);
        JButton btnPresupuestos = new JButton("PRESUPUESTOS");
        btnPresupuestos.addActionListener(this::btnPresupuestosActionPerformed);
        JButton btnCerrar = new JButton("CERRAR SESIÓN");
        btnCerrar.addActionListener(this::btnCerrarActionPerformed);

        JPanel botones = new JPanel(new GridLayout(4, 1, 8, 8));
        botones.add(btnDashboard);
        botones.add(btnMovimientos);
        botones.add(btnPresupuestos);
        botones.add(btnCerrar);

        setLayout(new BorderLayout(10, 10));
        add(titulo, BorderLayout.NORTH);
        add(botones, BorderLayout.CENTER);
        add(mensaje, BorderLayout.SOUTH);
    }

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {
        new Dashboard().setVisible(true);
        dispose();
    }

    private void btnMovimientosActionPerformed(java.awt.event.ActionEvent evt) {
        new MovimientosVentana().setVisible(true);
        dispose();
    }

    private void btnPresupuestosActionPerformed(java.awt.event.ActionEvent evt) {
        new PresupuestosVentana().setVisible(true);
        dispose();
    }

    private void btnCerrarActionPerformed(java.awt.event.ActionEvent evt) {
        new Login().setVisible(true);
        dispose();
    }
}

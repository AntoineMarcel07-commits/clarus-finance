package Interfaces;

import clarus.finance.ClarusFinance;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Login extends JFrame {

    public Login() {
        initComponents();
        setSize(520, 390);
        setLocationRelativeTo(null);
    }

    /**
     * Código creado por el diseñador visual de NetBeans.
     * Para mover controles usa la pestaña Design.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblSubtitulo = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        btnEntrar = new javax.swing.JButton();
        lblAyuda = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Clarus Finance - Iniciar sesión");
        setResizable(false);
        getContentPane().setBackground(new java.awt.Color(245, 247, 250));
        getContentPane().setLayout(null);

        lblTitulo.setFont(new java.awt.Font("SansSerif", 1, 28));
        lblTitulo.setForeground(new java.awt.Color(32, 68, 115));
        lblTitulo.setText("Clarus Finance");
        getContentPane().add(lblTitulo);
        lblTitulo.setBounds(155, 35, 220, 40);

        lblSubtitulo.setFont(new java.awt.Font("SansSerif", 0, 14));
        lblSubtitulo.setText("Control sencillo de ingresos y gastos");
        getContentPane().add(lblSubtitulo);
        lblSubtitulo.setBounds(135, 78, 280, 25);

        lblUsuario.setFont(new java.awt.Font("SansSerif", 1, 14));
        lblUsuario.setText("Usuario");
        getContentPane().add(lblUsuario);
        lblUsuario.setBounds(110, 125, 100, 25);

        txtUsuario.setFont(new java.awt.Font("SansSerif", 0, 14));
        txtUsuario.setText("admin");
        getContentPane().add(txtUsuario);
        txtUsuario.setBounds(110, 152, 300, 38);

        lblPassword.setFont(new java.awt.Font("SansSerif", 1, 14));
        lblPassword.setText("Contraseña");
        getContentPane().add(lblPassword);
        lblPassword.setBounds(110, 205, 120, 25);

        txtPassword.setFont(new java.awt.Font("SansSerif", 0, 14));
        txtPassword.setText("1234");
        txtPassword.addActionListener(this::btnEntrarActionPerformed);
        getContentPane().add(txtPassword);
        txtPassword.setBounds(110, 232, 300, 38);

        btnEntrar.setBackground(new java.awt.Color(32, 68, 115));
        btnEntrar.setFont(new java.awt.Font("SansSerif", 1, 14));
        btnEntrar.setForeground(new java.awt.Color(255, 255, 255));
        btnEntrar.setText("Entrar");
        btnEntrar.setBorderPainted(false);
        btnEntrar.setOpaque(true);
        btnEntrar.addActionListener(this::btnEntrarActionPerformed);
        getContentPane().add(btnEntrar);
        btnEntrar.setBounds(110, 288, 300, 42);

        lblAyuda.setFont(new java.awt.Font("SansSerif", 0, 12));
        lblAyuda.setText("Datos de prueba: admin / 1234");
        getContentPane().add(lblAyuda);
        lblAyuda.setBounds(165, 335, 220, 20);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEntrarActionPerformed(ActionEvent evt) {
        String usuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (usuario.equals("admin") && password.equals("1234")) {
            ClarusFinance.objMenuPrincipal = new MenuPrincipal();
            ClarusFinance.objMenuPrincipal.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEntrar;
    private javax.swing.JLabel lblAyuda;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblSubtitulo;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}

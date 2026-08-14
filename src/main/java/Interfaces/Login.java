package Interfaces;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame {
    JTextField txtUsuario;
    JPasswordField txtPassword;

    public Login() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Clarus Finance - Login");
        setSize(380, 230);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("CLARUS FINANCE", JLabel.CENTER);
        JLabel ayuda = new JLabel("Usuario: admin   Contraseña: 1234", JLabel.CENTER);

        txtUsuario = new JTextField();
        txtPassword = new JPasswordField();

        JPanel formulario = new JPanel(new GridLayout(2, 2, 8, 8));
        formulario.add(new JLabel("Usuario:"));
        formulario.add(txtUsuario);
        formulario.add(new JLabel("Contraseña:"));
        formulario.add(txtPassword);

        JButton btnEntrar = new JButton("ENTRAR");
        btnEntrar.addActionListener(this::btnEntrarActionPerformed);
        JPanel botones = new JPanel();
        botones.add(btnEntrar);

        JPanel centro = new JPanel(new BorderLayout(10, 10));
        centro.add(formulario, BorderLayout.CENTER);
        centro.add(ayuda, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(titulo, BorderLayout.NORTH);
        add(centro, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {
        String usuario = txtUsuario.getText();
        String password = new String(txtPassword.getPassword());

        if (usuario.equals("admin") && password.equals("1234")) {
            MenuPrincipal objMenu = new MenuPrincipal();
            objMenu.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos");
        }
    }
}

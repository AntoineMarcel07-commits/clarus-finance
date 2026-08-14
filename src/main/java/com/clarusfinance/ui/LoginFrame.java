package com.clarusfinance.ui;

import com.clarusfinance.application.service.AuthService;
import com.clarusfinance.domain.model.UserAccount;
import com.clarusfinance.ui.components.UiFactory;
import com.clarusfinance.ui.components.UiMessages;
import com.clarusfinance.ui.components.UiTheme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public final class LoginFrame extends JFrame {
    private final AuthService authService;
    private final Consumer<UserAccount> onAuthenticated;
    private final JTextField username = new JTextField("admin", 20);
    private final JPasswordField password = new JPasswordField(20);

    public LoginFrame(AuthService authService, Consumer<UserAccount> onAuthenticated) {
        this.authService = Objects.requireNonNull(authService);
        this.onAuthenticated = Objects.requireNonNull(onAuthenticated);
        build();
    }

    private void build() {
        setTitle("Clarus Finance - Iniciar sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(860, 520);
        setMinimumSize(new Dimension(760, 480));
        setLocationRelativeTo(null);

        JPanel brand = new JPanel();
        brand.setBackground(UiTheme.NAVY);
        brand.setPreferredSize(new Dimension(350, 520));
        brand.setBorder(BorderFactory.createEmptyBorder(70, 42, 60, 42));
        brand.setLayout(new BoxLayout(brand, BoxLayout.Y_AXIS));
        JLabel mark = new JLabel("C$");
        mark.setForeground(Color.WHITE);
        mark.setFont(UiTheme.FONT.deriveFont(Font.BOLD, 46f));
        JLabel name = new JLabel("Clarus Finance");
        name.setForeground(Color.WHITE);
        name.setFont(UiTheme.FONT.deriveFont(Font.BOLD, 29f));
        JLabel slogan = new JLabel("Decisiones claras para tu dinero.");
        slogan.setForeground(new Color(191, 219, 254));
        slogan.setFont(UiTheme.FONT.deriveFont(15f));
        brand.add(mark);
        brand.add(Box.createVerticalStrut(18));
        brand.add(name);
        brand.add(Box.createVerticalStrut(10));
        brand.add(slogan);
        brand.add(Box.createVerticalGlue());
        JLabel version = new JLabel("Versión 1.0.0");
        version.setForeground(new Color(148, 163, 184));
        brand.add(version);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(UiTheme.BACKGROUND);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        constraints.insets = new Insets(6, 60, 6, 60);
        constraints.gridy = 0;
        JLabel title = UiFactory.title("Bienvenido");
        form.add(title, constraints);
        constraints.gridy++;
        form.add(UiFactory.muted("Ingresa a tu panel de finanzas personales"), constraints);
        constraints.insets = new Insets(24, 60, 5, 60);
        constraints.gridy++;
        form.add(new JLabel("Usuario"), constraints);
        constraints.insets = new Insets(4, 60, 10, 60);
        constraints.gridy++;
        form.add(username, constraints);
        constraints.insets = new Insets(8, 60, 5, 60);
        constraints.gridy++;
        form.add(new JLabel("Contraseña"), constraints);
        constraints.insets = new Insets(4, 60, 18, 60);
        constraints.gridy++;
        form.add(password, constraints);
        var login = UiFactory.primaryButton("Entrar");
        login.addActionListener(event -> authenticate());
        constraints.gridy++;
        form.add(login, constraints);
        constraints.insets = new Insets(14, 60, 4, 60);
        constraints.gridy++;
        form.add(UiFactory.muted("Demo: admin / Clarus123!"), constraints);

        add(brand, BorderLayout.WEST);
        add(form, BorderLayout.CENTER);
        getRootPane().setDefaultButton(login);
    }

    private void authenticate() {
        char[] rawPassword = password.getPassword();
        try {
            UserAccount user = authService.authenticate(username.getText(), rawPassword);
            onAuthenticated.accept(user);
            dispose();
        } catch (RuntimeException exception) {
            UiMessages.error(this, exception);
            password.selectAll();
            password.requestFocusInWindow();
        } finally {
            Arrays.fill(rawPassword, '\0');
        }
    }
}

package com.clarusfinance.ui;

import com.clarusfinance.application.service.BudgetService;
import com.clarusfinance.application.service.DashboardService;
import com.clarusfinance.application.service.MovementService;
import com.clarusfinance.domain.model.UserAccount;
import com.clarusfinance.ui.components.UiFactory;
import com.clarusfinance.ui.components.UiTheme;
import com.clarusfinance.ui.panels.BudgetsPanel;
import com.clarusfinance.ui.panels.DashboardPanel;
import com.clarusfinance.ui.panels.MovementsPanel;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public final class MainFrame extends JFrame {
    private static final String DASHBOARD = "dashboard";
    private static final String MOVEMENTS = "movements";
    private static final String BUDGETS = "budgets";

    private final Runnable onLogout;
    private final CardLayout cardLayout = new CardLayout();
    private final JPanel content = new JPanel(cardLayout);
    private final DashboardPanel dashboardPanel;
    private final MovementsPanel movementsPanel;
    private final BudgetsPanel budgetsPanel;

    public MainFrame(UserAccount user, DashboardService dashboardService,
            MovementService movementService, BudgetService budgetService, Runnable onLogout) {
        Objects.requireNonNull(user);
        this.onLogout = Objects.requireNonNull(onLogout);
        dashboardPanel = new DashboardPanel(dashboardService);
        movementsPanel = new MovementsPanel(movementService, this::refreshAll);
        budgetsPanel = new BudgetsPanel(budgetService, this::refreshAll);
        build(user);
    }

    private void build(UserAccount user) {
        setTitle("Clarus Finance");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1180, 760);
        setMinimumSize(new Dimension(980, 640));
        setLocationRelativeTo(null);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(UiTheme.NAVY);
        sidebar.setBorder(BorderFactory.createEmptyBorder(28, 18, 22, 18));
        sidebar.setPreferredSize(new Dimension(245, 760));
        JLabel brand = new JLabel("C$  Clarus Finance");
        brand.setForeground(Color.WHITE);
        brand.setFont(UiTheme.FONT.deriveFont(Font.BOLD, 20f));
        brand.setAlignmentX(LEFT_ALIGNMENT);
        sidebar.add(brand);
        sidebar.add(Box.createVerticalStrut(38));
        sidebar.add(nav("Resumen", DASHBOARD));
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(nav("Movimientos", MOVEMENTS));
        sidebar.add(Box.createVerticalStrut(8));
        sidebar.add(nav("Presupuestos", BUDGETS));
        sidebar.add(Box.createVerticalGlue());
        JLabel version = new JLabel("Clarus Finance v1.0.0");
        version.setForeground(new Color(148, 163, 184));
        version.setAlignmentX(LEFT_ALIGNMENT);
        sidebar.add(version);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, UiTheme.BORDER),
                BorderFactory.createEmptyBorder(12, 24, 12, 24)));
        JLabel greeting = new JLabel("Hola, " + user.displayName());
        greeting.setFont(UiTheme.FONT.deriveFont(Font.BOLD, 15f));
        JPanel account = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        account.setOpaque(false);
        account.add(UiFactory.muted("Sesión: " + user.username()));
        JButton logout = UiFactory.secondaryButton("Cerrar sesión");
        logout.addActionListener(event -> logout());
        account.add(logout);
        header.add(greeting, BorderLayout.WEST);
        header.add(account, BorderLayout.EAST);

        content.add(dashboardPanel, DASHBOARD);
        content.add(movementsPanel, MOVEMENTS);
        content.add(budgetsPanel, BUDGETS);

        JPanel main = new JPanel(new BorderLayout());
        main.add(header, BorderLayout.NORTH);
        main.add(content, BorderLayout.CENTER);
        add(sidebar, BorderLayout.WEST);
        add(main, BorderLayout.CENTER);
        cardLayout.show(content, DASHBOARD);
    }

    private JButton nav(String text, String destination) {
        JButton button = UiFactory.navButton(text);
        button.setAlignmentX(LEFT_ALIGNMENT);
        button.addActionListener(event -> {
            refresh(destination);
            cardLayout.show(content, destination);
        });
        return button;
    }

    private void refresh(String destination) {
        switch (destination) {
            case DASHBOARD -> dashboardPanel.refreshData();
            case MOVEMENTS -> movementsPanel.refreshData();
            case BUDGETS -> budgetsPanel.refreshData();
            default -> throw new IllegalArgumentException("Módulo desconocido: " + destination);
        }
    }

    private void refreshAll() {
        dashboardPanel.refreshData();
        budgetsPanel.refreshData();
    }

    private void logout() {
        dispose();
        onLogout.run();
    }
}

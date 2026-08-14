package com.clarusfinance;

import com.clarusfinance.application.service.AuthService;
import com.clarusfinance.application.service.BudgetService;
import com.clarusfinance.application.service.DashboardService;
import com.clarusfinance.application.service.MovementService;
import com.clarusfinance.domain.repository.BudgetRepository;
import com.clarusfinance.domain.repository.MovementRepository;
import com.clarusfinance.domain.repository.UserRepository;
import com.clarusfinance.infrastructure.config.AppConfig;
import com.clarusfinance.infrastructure.db.ConnectionFactory;
import com.clarusfinance.infrastructure.db.DatabaseInitializer;
import com.clarusfinance.infrastructure.db.PostgresConnectionFactory;
import com.clarusfinance.infrastructure.repository.JdbcBudgetRepository;
import com.clarusfinance.infrastructure.repository.JdbcMovementRepository;
import com.clarusfinance.infrastructure.repository.JdbcUserRepository;
import com.clarusfinance.infrastructure.security.Pbkdf2PasswordHasher;
import com.clarusfinance.ui.LoginFrame;
import com.clarusfinance.ui.MainFrame;
import com.clarusfinance.ui.components.UiTheme;
import java.awt.GraphicsEnvironment;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public final class ClarusFinanceApp {
    private final AuthService authService;
    private final MovementService movementService;
    private final BudgetService budgetService;
    private final DashboardService dashboardService;

    private ClarusFinanceApp() {
        AppConfig config = AppConfig.load();
        ConnectionFactory connectionFactory = new PostgresConnectionFactory(config);
        new DatabaseInitializer(connectionFactory).initialize();

        MovementRepository movementRepository = new JdbcMovementRepository(connectionFactory);
        BudgetRepository budgetRepository = new JdbcBudgetRepository(connectionFactory);
        UserRepository userRepository = new JdbcUserRepository(connectionFactory);

        authService = new AuthService(userRepository, new Pbkdf2PasswordHasher());
        movementService = new MovementService(movementRepository);
        budgetService = new BudgetService(budgetRepository, movementRepository);
        dashboardService = new DashboardService(movementRepository, budgetRepository);
    }

    public static void main(String[] args) {
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("Clarus Finance requiere un entorno gráfico.");
            return;
        }
        SwingUtilities.invokeLater(() -> {
            UiTheme.install();
            try {
                new ClarusFinanceApp().showLogin();
            } catch (RuntimeException exception) {
                JOptionPane.showMessageDialog(null,
                        "No fue posible iniciar Clarus Finance.\n\n"
                        + "1. Crea la base de datos clarus_finance.\n"
                        + "2. Configura config/application.properties.\n\n"
                        + "Detalle: " + rootMessage(exception),
                        "Error de conexión", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void showLogin() {
        new LoginFrame(authService, user -> new MainFrame(
                user,
                dashboardService,
                movementService,
                budgetService,
                this::showLogin).setVisible(true)).setVisible(true);
    }

    private static String rootMessage(Throwable throwable) {
        Throwable root = throwable;
        while (root.getCause() != null) {
            root = root.getCause();
        }
        return root.getMessage() == null ? root.getClass().getSimpleName() : root.getMessage();
    }
}

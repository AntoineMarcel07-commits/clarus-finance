package com.clarusfinance.ui.panels;

import com.clarusfinance.application.service.DashboardService;
import com.clarusfinance.domain.model.DashboardSummary;
import com.clarusfinance.domain.model.Movement;
import com.clarusfinance.ui.components.PeriodSpinner;
import com.clarusfinance.ui.components.UiFactory;
import com.clarusfinance.ui.components.UiMessages;
import com.clarusfinance.ui.components.UiTheme;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public final class DashboardPanel extends JPanel {
    private final DashboardService service;
    private final PeriodSpinner period = new PeriodSpinner();
    private final JLabel income = valueLabel(UiTheme.GREEN);
    private final JLabel expense = valueLabel(UiTheme.RED);
    private final JLabel balance = valueLabel(UiTheme.BLUE);
    private final JLabel budget = valueLabel(UiTheme.AMBER);
    private final JProgressBar budgetProgress = new JProgressBar();
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"Fecha", "Tipo", "Categoría", "Descripción", "Monto"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    public DashboardPanel(DashboardService service) {
        this.service = Objects.requireNonNull(service);
        build();
        refreshData();
    }

    private void build() {
        setLayout(new BorderLayout(0, 20));
        setBackground(UiTheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(26, 28, 26, 28));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JPanel heading = new JPanel(new GridLayout(2, 1));
        heading.setOpaque(false);
        heading.add(UiFactory.title("Dashboard"));
        heading.add(UiFactory.muted("Resumen de tus finanzas personales"));
        JPanel periodActions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        periodActions.setOpaque(false);
        periodActions.add(new JLabel("Periodo:"));
        periodActions.add(period);
        var refresh = UiFactory.secondaryButton("Actualizar");
        refresh.addActionListener(event -> refreshData());
        periodActions.add(refresh);
        header.add(heading, BorderLayout.WEST);
        header.add(periodActions, BorderLayout.EAST);

        JPanel cards = new JPanel(new GridLayout(1, 4, 14, 0));
        cards.setOpaque(false);
        cards.add(card("Ingresos", income, null));
        cards.add(card("Gastos", expense, null));
        cards.add(card("Balance", balance, null));
        cards.add(card("Uso de presupuesto", budget, budgetProgress));

        JPanel top = new JPanel(new BorderLayout(0, 20));
        top.setOpaque(false);
        top.add(header, BorderLayout.NORTH);
        top.add(cards, BorderLayout.CENTER);

        JTable table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setSelectionBackground(new Color(219, 234, 254));
        JPanel recent = new JPanel(new BorderLayout(0, 12));
        recent.setBackground(UiTheme.SURFACE);
        recent.setBorder(UiTheme.CARD_BORDER);
        JLabel recentTitle = new JLabel("Movimientos recientes");
        recentTitle.setFont(UiTheme.FONT.deriveFont(Font.BOLD, 17f));
        recent.add(recentTitle, BorderLayout.NORTH);
        recent.add(new JScrollPane(table), BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);
        add(recent, BorderLayout.CENTER);
    }

    private JPanel card(String title, JLabel value, JProgressBar progress) {
        JPanel card = new JPanel(new BorderLayout(0, 10));
        card.setBackground(UiTheme.SURFACE);
        card.setBorder(UiTheme.CARD_BORDER);
        JLabel label = new JLabel(title);
        label.setForeground(UiTheme.MUTED);
        card.add(label, BorderLayout.NORTH);
        card.add(value, BorderLayout.CENTER);
        if (progress != null) {
            progress.setStringPainted(false);
            progress.setForeground(UiTheme.AMBER);
            card.add(progress, BorderLayout.SOUTH);
        }
        return card;
    }

    private static JLabel valueLabel(Color color) {
        JLabel label = new JLabel("$0.00");
        label.setForeground(color);
        label.setFont(UiTheme.FONT.deriveFont(Font.BOLD, 22f));
        return label;
    }

    public void refreshData() {
        try {
            DashboardSummary summary = service.summarize(period.period());
            income.setText(UiFactory.money(summary.totalIncome()));
            expense.setText(UiFactory.money(summary.totalExpense()));
            balance.setText(UiFactory.money(summary.balance()));
            balance.setForeground(summary.balance().compareTo(BigDecimal.ZERO) >= 0 ? UiTheme.BLUE : UiTheme.RED);
            budget.setText(summary.budgetUsagePercentage() + "%");
            budgetProgress.setMaximum(Math.max(100, summary.budgetUsagePercentage()));
            budgetProgress.setValue(summary.budgetUsagePercentage());
            model.setRowCount(0);
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (Movement movement : summary.recentMovements()) {
                model.addRow(new Object[]{
                    movement.date().format(dateFormat),
                    movement.type().displayName(),
                    movement.category(),
                    movement.description(),
                    UiFactory.money(movement.amount())
                });
            }
        } catch (RuntimeException exception) {
            UiMessages.error(this, exception);
        }
    }
}

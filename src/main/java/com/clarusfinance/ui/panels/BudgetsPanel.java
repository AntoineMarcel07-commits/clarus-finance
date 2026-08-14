package com.clarusfinance.ui.panels;

import com.clarusfinance.application.service.BudgetService;
import com.clarusfinance.domain.model.Budget;
import com.clarusfinance.domain.model.BudgetProgress;
import com.clarusfinance.ui.components.PeriodSpinner;
import com.clarusfinance.ui.components.UiFactory;
import com.clarusfinance.ui.components.UiMessages;
import com.clarusfinance.ui.components.UiTheme;
import com.clarusfinance.ui.dialogs.BudgetDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public final class BudgetsPanel extends JPanel {
    private final BudgetService service;
    private final Runnable onDataChanged;
    private final PeriodSpinner period = new PeriodSpinner();
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Categoría", "Límite", "Gastado", "Restante", "Uso", "Estado"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(model);
    private List<BudgetProgress> budgets = new ArrayList<>();

    public BudgetsPanel(BudgetService service, Runnable onDataChanged) {
        this.service = Objects.requireNonNull(service);
        this.onDataChanged = Objects.requireNonNull(onDataChanged);
        build();
        refreshData();
    }

    private void build() {
        setLayout(new BorderLayout(0, 18));
        setBackground(UiTheme.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(26, 28, 26, 28));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JPanel heading = new JPanel(new GridLayout(2, 1));
        heading.setOpaque(false);
        heading.add(UiFactory.title("Presupuestos"));
        heading.add(UiFactory.muted("Define límites mensuales y vigila su avance"));
        JPanel filters = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filters.setOpaque(false);
        filters.add(new JLabel("Periodo:"));
        filters.add(period);
        var refresh = UiFactory.secondaryButton("Consultar");
        refresh.addActionListener(event -> refreshData());
        filters.add(refresh);
        header.add(heading, BorderLayout.WEST);
        header.add(filters, BorderLayout.EAST);

        table.setFillsViewportHeight(true);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.getColumnModel().getColumn(0).setMaxWidth(60);
        JPanel tableCard = new JPanel(new BorderLayout());
        tableCard.setBackground(UiTheme.SURFACE);
        tableCard.setBorder(UiTheme.CARD_BORDER);
        tableCard.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        actions.setOpaque(false);
        var add = UiFactory.primaryButton("Nuevo presupuesto");
        add.addActionListener(event -> addBudget());
        var edit = UiFactory.secondaryButton("Editar");
        edit.addActionListener(event -> editBudget());
        var delete = UiFactory.dangerButton("Eliminar");
        delete.addActionListener(event -> deleteBudget());
        actions.add(add);
        actions.add(edit);
        actions.add(delete);

        add(header, BorderLayout.NORTH);
        add(tableCard, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);
    }

    public void refreshData() {
        try {
            budgets = service.progressFor(period.period());
            model.setRowCount(0);
            for (BudgetProgress progress : budgets) {
                model.addRow(new Object[]{
                    progress.budget().id(),
                    progress.budget().category(),
                    UiFactory.money(progress.budget().monthlyLimit()),
                    UiFactory.money(progress.spent()),
                    UiFactory.money(progress.remaining()),
                    progress.percentage() + "%",
                    progress.status().displayName()
                });
            }
        } catch (RuntimeException exception) {
            UiMessages.error(this, exception);
        }
    }

    private void addBudget() {
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        new BudgetDialog(owner, null, period.period()).open().ifPresent(data -> {
            try {
                service.create(data.category(), data.limit(), data.period());
                period.setPeriod(data.period());
                changed("Presupuesto guardado correctamente");
            } catch (RuntimeException exception) {
                UiMessages.error(this, exception);
            }
        });
    }

    private void editBudget() {
        Budget selected = selectedBudget();
        if (selected == null) {
            return;
        }
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        new BudgetDialog(owner, selected, period.period()).open().ifPresent(data -> {
            try {
                service.update(selected.id(), data.category(), data.limit(), data.period());
                period.setPeriod(data.period());
                changed("Presupuesto actualizado correctamente");
            } catch (RuntimeException exception) {
                UiMessages.error(this, exception);
            }
        });
    }

    private void deleteBudget() {
        Budget selected = selectedBudget();
        if (selected == null) {
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el presupuesto de " + selected.category() + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) {
            try {
                service.delete(selected.id());
                changed("Presupuesto eliminado");
            } catch (RuntimeException exception) {
                UiMessages.error(this, exception);
            }
        }
    }

    private Budget selectedBudget() {
        int row = table.getSelectedRow();
        if (row < 0) {
            UiMessages.info(this, "Selecciona un presupuesto en la tabla");
            return null;
        }
        return budgets.get(table.convertRowIndexToModel(row)).budget();
    }

    private void changed(String message) {
        refreshData();
        onDataChanged.run();
        UiMessages.info(this, message);
    }
}

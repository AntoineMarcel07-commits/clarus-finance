package com.clarusfinance.ui.dialogs;

import com.clarusfinance.domain.model.Budget;
import com.clarusfinance.ui.components.PeriodSpinner;
import com.clarusfinance.ui.components.UiFactory;
import com.clarusfinance.ui.components.UiMessages;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.Optional;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class BudgetDialog extends JDialog {
    private static final String[] CATEGORIES = {
        "Alimentación", "Transporte", "Vivienda", "Servicios", "Salud",
        "Educación", "Entretenimiento", "Ahorro", "Otros"
    };

    private final JComboBox<String> category = new JComboBox<>(CATEGORIES);
    private final JTextField limit = new JTextField(18);
    private final PeriodSpinner period = new PeriodSpinner();
    private BudgetFormData result;

    public BudgetDialog(Frame owner, Budget budget, YearMonth defaultPeriod) {
        super(owner, budget == null ? "Nuevo presupuesto" : "Editar presupuesto", true);
        build(budget, defaultPeriod);
    }

    public Optional<BudgetFormData> open() {
        setVisible(true);
        return Optional.ofNullable(result);
    }

    private void build(Budget budget, YearMonth defaultPeriod) {
        setSize(460, 300);
        setResizable(false);
        setLocationRelativeTo(getOwner());
        category.setEditable(true);
        period.setPeriod(defaultPeriod);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 24, 12, 24));
        addRow(form, 0, "Categoría", category);
        addRow(form, 1, "Límite mensual", limit);
        addRow(form, 2, "Periodo", period);

        if (budget != null) {
            category.setSelectedItem(budget.category());
            limit.setText(budget.monthlyLimit().toPlainString());
            period.setPeriod(budget.period());
        }

        var save = UiFactory.primaryButton("Guardar");
        save.addActionListener(event -> save());
        var cancel = UiFactory.secondaryButton("Cancelar");
        cancel.addActionListener(event -> dispose());
        JPanel actions = new JPanel();
        actions.add(cancel);
        actions.add(save);

        add(form, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(save);
    }

    private void addRow(JPanel panel, int row, String label, java.awt.Component input) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(8, 6, 8, 6);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = row;
        panel.add(new JLabel(label), constraints);
        constraints.gridx = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 1;
        panel.add(input, constraints);
    }

    private void save() {
        try {
            result = new BudgetFormData(
                    String.valueOf(category.getSelectedItem()),
                    new BigDecimal(limit.getText().trim()),
                    period.period());
            dispose();
        } catch (NumberFormatException exception) {
            UiMessages.error(this, new IllegalArgumentException("Escribe un límite válido, por ejemplo 2000.00"));
        }
    }

    public record BudgetFormData(String category, BigDecimal limit, YearMonth period) {
    }
}

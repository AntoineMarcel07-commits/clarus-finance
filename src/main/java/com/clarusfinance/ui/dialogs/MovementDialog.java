package com.clarusfinance.ui.dialogs;

import com.clarusfinance.domain.model.Movement;
import com.clarusfinance.domain.model.MovementType;
import com.clarusfinance.ui.components.UiFactory;
import com.clarusfinance.ui.components.UiMessages;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

public final class MovementDialog extends JDialog {
    private static final String[] CATEGORIES = {
        "Alimentación", "Transporte", "Vivienda", "Servicios", "Salud",
        "Educación", "Entretenimiento", "Sueldo", "Ahorro", "Otros"
    };

    private final JComboBox<MovementType> type = new JComboBox<>(MovementType.values());
    private final JTextField amount = new JTextField(18);
    private final JComboBox<String> category = new JComboBox<>(CATEGORIES);
    private final JSpinner date = new JSpinner(new SpinnerDateModel());
    private final JTextField description = new JTextField(18);
    private MovementFormData result;

    public MovementDialog(Frame owner, Movement movement) {
        super(owner, movement == null ? "Nuevo movimiento" : "Editar movimiento", true);
        build(movement);
    }

    public Optional<MovementFormData> open() {
        setVisible(true);
        return Optional.ofNullable(result);
    }

    private void build(Movement movement) {
        setSize(470, 390);
        setResizable(false);
        setLocationRelativeTo(getOwner());
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 24, 12, 24));
        date.setEditor(new JSpinner.DateEditor(date, "yyyy-MM-dd"));
        category.setEditable(true);

        addRow(form, 0, "Tipo", type);
        addRow(form, 1, "Monto", amount);
        addRow(form, 2, "Categoría", category);
        addRow(form, 3, "Fecha", date);
        addRow(form, 4, "Descripción", description);

        if (movement != null) {
            type.setSelectedItem(movement.type());
            amount.setText(movement.amount().toPlainString());
            category.setSelectedItem(movement.category());
            date.setValue(Date.from(movement.date().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            description.setText(movement.description());
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
            Date selectedDate = (Date) date.getValue();
            LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            result = new MovementFormData(
                    (MovementType) type.getSelectedItem(),
                    new BigDecimal(amount.getText().trim()),
                    String.valueOf(category.getSelectedItem()),
                    localDate,
                    description.getText().trim());
            dispose();
        } catch (NumberFormatException exception) {
            UiMessages.error(this, new IllegalArgumentException("Escribe un monto válido, por ejemplo 350.50"));
        }
    }

    public record MovementFormData(
            MovementType type,
            BigDecimal amount,
            String category,
            LocalDate date,
            String description) {
    }
}

package com.clarusfinance.ui.panels;

import com.clarusfinance.application.service.MovementService;
import com.clarusfinance.domain.model.Movement;
import com.clarusfinance.ui.components.PeriodSpinner;
import com.clarusfinance.ui.components.UiFactory;
import com.clarusfinance.ui.components.UiMessages;
import com.clarusfinance.ui.components.UiTheme;
import com.clarusfinance.ui.dialogs.MovementDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

public final class MovementsPanel extends JPanel {
    private final MovementService service;
    private final Runnable onDataChanged;
    private final PeriodSpinner period = new PeriodSpinner();
    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"ID", "Fecha", "Tipo", "Categoría", "Descripción", "Monto"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(model);
    private List<Movement> movements = new ArrayList<>();

    public MovementsPanel(MovementService service, Runnable onDataChanged) {
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
        heading.add(UiFactory.title("Movimientos"));
        heading.add(UiFactory.muted("Registra ingresos y gastos, edita o exporta el mes"));
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
        var add = UiFactory.primaryButton("Nuevo movimiento");
        add.addActionListener(event -> addMovement());
        var edit = UiFactory.secondaryButton("Editar");
        edit.addActionListener(event -> editMovement());
        var delete = UiFactory.dangerButton("Eliminar");
        delete.addActionListener(event -> deleteMovement());
        var export = UiFactory.secondaryButton("Exportar CSV");
        export.addActionListener(event -> exportCsv());
        actions.add(add);
        actions.add(edit);
        actions.add(delete);
        actions.add(export);

        add(header, BorderLayout.NORTH);
        add(tableCard, BorderLayout.CENTER);
        add(actions, BorderLayout.SOUTH);
    }

    public void refreshData() {
        try {
            movements = service.list(period.period());
            model.setRowCount(0);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            for (Movement movement : movements) {
                model.addRow(new Object[]{
                    movement.id(),
                    movement.date().format(formatter),
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

    private void addMovement() {
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        new MovementDialog(owner, null).open().ifPresent(data -> {
            try {
                service.create(data.type(), data.amount(), data.category(), data.date(), data.description());
                changed("Movimiento guardado correctamente");
            } catch (RuntimeException exception) {
                UiMessages.error(this, exception);
            }
        });
    }

    private void editMovement() {
        Movement selected = selectedMovement();
        if (selected == null) {
            return;
        }
        Frame owner = (Frame) SwingUtilities.getWindowAncestor(this);
        new MovementDialog(owner, selected).open().ifPresent(data -> {
            try {
                service.update(selected.id(), data.type(), data.amount(), data.category(), data.date(), data.description());
                changed("Movimiento actualizado correctamente");
            } catch (RuntimeException exception) {
                UiMessages.error(this, exception);
            }
        });
    }

    private void deleteMovement() {
        Movement selected = selectedMovement();
        if (selected == null) {
            return;
        }
        int answer = JOptionPane.showConfirmDialog(this,
                "¿Eliminar el movimiento seleccionado?", "Confirmar",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (answer == JOptionPane.YES_OPTION) {
            try {
                service.delete(selected.id());
                changed("Movimiento eliminado");
            } catch (RuntimeException exception) {
                UiMessages.error(this, exception);
            }
        }
    }

    private Movement selectedMovement() {
        int row = table.getSelectedRow();
        if (row < 0) {
            UiMessages.info(this, "Selecciona un movimiento en la tabla");
            return null;
        }
        return movements.get(table.convertRowIndexToModel(row));
    }

    private void changed(String message) {
        refreshData();
        onDataChanged.run();
        UiMessages.info(this, message);
    }

    private void exportCsv() {
        if (movements.isEmpty()) {
            UiMessages.info(this, "No hay movimientos para exportar en este periodo");
            return;
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Guardar reporte de movimientos");
        chooser.setSelectedFile(new java.io.File("movimientos-" + period.period() + ".csv"));
        chooser.setFileFilter(new FileNameExtensionFilter("Archivo CSV", "csv"));
        if (chooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        Path output = chooser.getSelectedFile().toPath();
        if (!output.getFileName().toString().toLowerCase().endsWith(".csv")) {
            output = output.resolveSibling(output.getFileName() + ".csv");
        }
        try {
            StringBuilder csv = new StringBuilder("Fecha,Tipo,Categoria,Descripcion,Monto\n");
            for (Movement movement : movements) {
                csv.append(movement.date()).append(',')
                        .append(movement.type().displayName()).append(',')
                        .append(csvField(movement.category())).append(',')
                        .append(csvField(movement.description())).append(',')
                        .append(movement.amount().toPlainString()).append('\n');
            }
            Files.writeString(output, csv.toString(), StandardCharsets.UTF_8);
            UiMessages.info(this, "Reporte guardado en:\n" + output.toAbsolutePath());
        } catch (IOException exception) {
            UiMessages.error(this, exception);
        }
    }

    private String csvField(String value) {
        return '"' + value.replace("\"", "\"\"") + '"';
    }
}

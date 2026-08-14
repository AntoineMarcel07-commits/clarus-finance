package Interfaces;

import clarus.finance.Movimiento;
import clarus.finance.Presupuesto;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import static clarus.finance.ClarusFinance.objCalculos;
import static clarus.finance.ClarusFinance.objConnection;
import static clarus.finance.ClarusFinance.objMovimiento;
import static clarus.finance.ClarusFinance.objPresupuesto;

public class Dashboard extends JFrame {
    JLabel lblIngresos;
    JLabel lblGastos;
    JLabel lblSaldo;
    JTextArea areaMovimientos;
    JTextArea areaPresupuestos;

    public Dashboard() {
        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setTitle("Clarus Finance - Dashboard");
        setSize(820, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titulo = new JLabel("DASHBOARD", JLabel.CENTER);

        lblIngresos = new JLabel("Ingresos: $0.00", JLabel.CENTER);
        lblGastos = new JLabel("Gastos: $0.00", JLabel.CENTER);
        lblSaldo = new JLabel("Saldo: $0.00", JLabel.CENTER);

        JPanel totales = new JPanel(new GridLayout(1, 3, 8, 8));
        totales.add(lblIngresos);
        totales.add(lblGastos);
        totales.add(lblSaldo);

        areaMovimientos = new JTextArea();
        areaMovimientos.setEditable(false);
        areaPresupuestos = new JTextArea();
        areaPresupuestos.setEditable(false);

        JPanel listas = new JPanel(new GridLayout(1, 2, 8, 8));
        listas.add(new JScrollPane(areaMovimientos));
        listas.add(new JScrollPane(areaPresupuestos));

        JButton btnActualizar = new JButton("ACTUALIZAR");
        btnActualizar.addActionListener(this::btnActualizarActionPerformed);
        JButton btnVolver = new JButton("VOLVER AL MENÚ");
        btnVolver.addActionListener(this::btnVolverActionPerformed);
        JPanel botones = new JPanel();
        botones.add(btnActualizar);
        botones.add(btnVolver);

        JPanel arriba = new JPanel(new BorderLayout());
        arriba.add(titulo, BorderLayout.NORTH);
        arriba.add(totales, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(arriba, BorderLayout.NORTH);
        add(listas, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);
    }

    private void cargarDatos() {
        ArrayList<Movimiento> movimientos = objMovimiento.listar(objConnection);
        ArrayList<Presupuesto> presupuestos = objPresupuesto.listar(objConnection);

        lblIngresos.setText("Ingresos: $" + objCalculos.totalIngresos(movimientos));
        lblGastos.setText("Gastos: $" + objCalculos.totalGastos(movimientos));
        lblSaldo.setText("Saldo: $" + objCalculos.saldo(movimientos));

        String textoMovimientos = "ÚLTIMOS MOVIMIENTOS\n\n";
        int cantidad = Math.min(10, movimientos.size());
        for (int i = 0; i < cantidad; i++) {
            Movimiento movimiento = movimientos.get(i);
            textoMovimientos += movimiento.id + " | " + movimiento.tipo + " | "
                    + movimiento.categoria + " | $" + movimiento.monto + "\n";
        }
        if (movimientos.isEmpty()) {
            textoMovimientos += "No hay movimientos.";
        }
        areaMovimientos.setText(textoMovimientos);

        String textoPresupuestos = "ESTADO DE PRESUPUESTOS\n\n";
        for (Presupuesto presupuesto : presupuestos) {
            double gastado = objCalculos.gastosPorCategoria(
                    movimientos, presupuesto.categoria);
            String estado = objCalculos.estadoPresupuesto(presupuesto.limite, gastado);
            textoPresupuestos += presupuesto.categoria + " | $" + gastado
                    + " de $" + presupuesto.limite + " | " + estado + "\n";
        }
        if (presupuestos.isEmpty()) {
            textoPresupuestos += "No hay presupuestos.";
        }
        areaPresupuestos.setText(textoPresupuestos);
    }

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {
        cargarDatos();
    }

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {
        new MenuPrincipal().setVisible(true);
        dispose();
    }
}

package clarus.finance;

import Interfaces.Login;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

public class ClarusFinance {
    public static Connection objConnection;
    public static Movimiento objMovimiento;
    public static Presupuesto objPresupuesto;
    public static OperacionesCalculos objCalculos;

    public static void main(String[] args) {
        JPasswordField campoPassword = new JPasswordField();
        int opcion = JOptionPane.showConfirmDialog(null, campoPassword,
                "Contraseña de PostgreSQL", JOptionPane.OK_CANCEL_OPTION);

        if (opcion != JOptionPane.OK_OPTION) {
            return;
        }

        String password = new String(campoPassword.getPassword());
        objConnection = ConexionBD.conectar(password);

        if (objConnection == null) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo conectar. Revisa PostgreSQL y la base clarus_finance.");
            return;
        }

        if (!ConexionBD.prepararTablas(objConnection)) {
            JOptionPane.showMessageDialog(null, "No se pudieron preparar las tablas.");
            return;
        }

        objMovimiento = new Movimiento();
        objPresupuesto = new Presupuesto();
        objCalculos = new CalculosFinanzas();

        Login objLogin = new Login();
        objLogin.setVisible(true);
    }
}

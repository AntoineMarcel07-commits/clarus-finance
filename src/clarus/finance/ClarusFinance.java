package clarus.finance;

import Interfaces.Login;
import Interfaces.MenuPrincipal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class ClarusFinance {

    public static Connection objConnection;
    public static Login objLogin;
    public static MenuPrincipal objMenuPrincipal;

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/ClarusFinance";
        String usuario = "postgres";
        String password = "TU_CONTRASENA";

        try {
            objConnection = DriverManager.getConnection(url, usuario, password);
            if (objConnection != null) {
                System.out.println("Si se conectó mi bro");
                objLogin = new Login();
                objLogin.setVisible(true);
            }
        } catch (SQLException e) {
            System.err.println("No se conectó wey");
            System.err.println(e.toString());
            JOptionPane.showMessageDialog(
                    null,
                    "No se pudo conectar con PostgreSQL.\n"
                    + "Revisa que exista la base ClarusFinance y que la contraseña sea correcta.\n\n"
                    + e.getMessage(),
                    "Error de conexión",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void cerrarSesion() {
        if (objMenuPrincipal != null) {
            objMenuPrincipal.dispose();
        }
        objMenuPrincipal = null;
        objLogin = new Login();
        objLogin.setVisible(true);
    }
}

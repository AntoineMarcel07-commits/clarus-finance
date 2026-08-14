package clarus.finance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexionBD {
    public static final String URL = "jdbc:postgresql://localhost:5432/clarus_finance";
    public static final String USUARIO = "postgres";

    public static Connection conectar(String password) {
        try {
            return DriverManager.getConnection(URL, USUARIO, password);
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }

    public static boolean prepararTablas(Connection conexion) {
        String tablaMovimientos = "CREATE TABLE IF NOT EXISTS movimientos ("
                + "id SERIAL PRIMARY KEY, "
                + "tipo VARCHAR(10) NOT NULL, "
                + "descripcion VARCHAR(100) NOT NULL, "
                + "categoria VARCHAR(50) NOT NULL, "
                + "monto NUMERIC(10,2) NOT NULL, "
                + "fecha DATE NOT NULL)";

        String tablaPresupuestos = "CREATE TABLE IF NOT EXISTS presupuestos ("
                + "id SERIAL PRIMARY KEY, "
                + "categoria VARCHAR(50) UNIQUE NOT NULL, "
                + "limite NUMERIC(10,2) NOT NULL)";

        try {
            Statement statement = conexion.createStatement();
            statement.executeUpdate(tablaMovimientos);
            statement.executeUpdate(tablaPresupuestos);
            statement.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al preparar tablas: " + e.getMessage());
            return false;
        }
    }
}

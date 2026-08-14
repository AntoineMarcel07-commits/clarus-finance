package clarus.finance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Movimiento {
    public int id;
    public String fecha;
    public String tipo;
    public String categoria;
    public String descripcion;
    public double monto;

    public Movimiento InfoMovimiento(int id, String fecha, String tipo,
            String categoria, String descripcion, double monto) {
        Movimiento objMovimiento = new Movimiento();
        objMovimiento.id = id;
        objMovimiento.fecha = fecha;
        objMovimiento.tipo = tipo;
        objMovimiento.categoria = categoria;
        objMovimiento.descripcion = descripcion;
        objMovimiento.monto = monto;
        return objMovimiento;
    }

    public boolean insertar(Connection conexion, Movimiento movimiento) {
        String sql = "INSERT INTO movimientos(tipo, descripcion, categoria, monto, fecha) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, movimiento.tipo);
            ps.setString(2, movimiento.descripcion);
            ps.setString(3, movimiento.categoria);
            ps.setDouble(4, movimiento.monto);
            ps.setDate(5, java.sql.Date.valueOf(movimiento.fecha));
            int filas = ps.executeUpdate();
            ps.close();
            return filas > 0;
        } catch (Exception e) {
            System.out.println("Error al insertar movimiento: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizar(Connection conexion, Movimiento movimiento) {
        String sql = "UPDATE movimientos SET tipo=?, descripcion=?, categoria=?, monto=?, fecha=? "
                + "WHERE id=?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, movimiento.tipo);
            ps.setString(2, movimiento.descripcion);
            ps.setString(3, movimiento.categoria);
            ps.setDouble(4, movimiento.monto);
            ps.setDate(5, java.sql.Date.valueOf(movimiento.fecha));
            ps.setInt(6, movimiento.id);
            int filas = ps.executeUpdate();
            ps.close();
            return filas > 0;
        } catch (Exception e) {
            System.out.println("Error al actualizar movimiento: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(Connection conexion, int idMovimiento) {
        String sql = "DELETE FROM movimientos WHERE id=?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idMovimiento);
            int filas = ps.executeUpdate();
            ps.close();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar movimiento: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Movimiento> listar(Connection conexion) {
        ArrayList<Movimiento> lista = new ArrayList<>();
        String sql = "SELECT id, fecha, tipo, categoria, descripcion, monto "
                + "FROM movimientos ORDER BY fecha DESC, id DESC";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Movimiento movimiento = InfoMovimiento(
                        rs.getInt("id"),
                        rs.getString("fecha"),
                        rs.getString("tipo"),
                        rs.getString("categoria"),
                        rs.getString("descripcion"),
                        rs.getDouble("monto"));
                lista.add(movimiento);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar movimientos: " + e.getMessage());
        }

        return lista;
    }
}

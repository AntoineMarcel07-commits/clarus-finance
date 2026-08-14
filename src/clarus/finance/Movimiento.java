package clarus.finance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class Movimiento {

    public int id;
    public String fecha;
    public String tipo;
    public String categoria;
    public String descripcion;
    public double monto;

    public Movimiento InfoMovimiento(int id, String fecha, String tipo,
            String categoria, String descripcion, double monto) {
        Movimiento movimiento = new Movimiento();
        movimiento.id = id;
        movimiento.fecha = fecha;
        movimiento.tipo = tipo;
        movimiento.categoria = categoria;
        movimiento.descripcion = descripcion;
        movimiento.monto = monto;
        return movimiento;
    }

    public boolean insertar(Connection conexion, Movimiento movimiento) {
        String query = "INSERT INTO movimientos(fecha, tipo, categoria, descripcion, monto) "
                + "VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setDate(1, java.sql.Date.valueOf(movimiento.fecha));
            ps.setString(2, movimiento.tipo);
            ps.setString(3, movimiento.categoria);
            ps.setString(4, movimiento.descripcion);
            ps.setDouble(5, movimiento.monto);
            int filas = ps.executeUpdate();
            ps.close();
            return filas > 0;
        } catch (Exception e) {
            System.err.println("Error al guardar movimiento");
            System.err.println(e.toString());
            return false;
        }
    }

    public boolean actualizar(Connection conexion, Movimiento movimiento) {
        String query = "UPDATE movimientos SET fecha = ?, tipo = ?, categoria = ?, "
                + "descripcion = ?, monto = ? WHERE id_movimiento = ?";
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setDate(1, java.sql.Date.valueOf(movimiento.fecha));
            ps.setString(2, movimiento.tipo);
            ps.setString(3, movimiento.categoria);
            ps.setString(4, movimiento.descripcion);
            ps.setDouble(5, movimiento.monto);
            ps.setInt(6, movimiento.id);
            int filas = ps.executeUpdate();
            ps.close();
            return filas > 0;
        } catch (Exception e) {
            System.err.println("Error al actualizar movimiento");
            System.err.println(e.toString());
            return false;
        }
    }

    public boolean eliminar(Connection conexion, int idMovimiento) {
        try {
            PreparedStatement ps = conexion.prepareStatement(
                    "DELETE FROM movimientos WHERE id_movimiento = ?");
            ps.setInt(1, idMovimiento);
            int filas = ps.executeUpdate();
            ps.close();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar movimiento");
            System.err.println(e.toString());
            return false;
        }
    }

    public ArrayList<Movimiento> listar(Connection conexion) {
        ArrayList<Movimiento> lista = new ArrayList<>();
        String query = "SELECT id_movimiento, fecha, tipo, categoria, descripcion, monto "
                + "FROM movimientos ORDER BY fecha DESC, id_movimiento DESC";
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(InfoMovimiento(
                        rs.getInt("id_movimiento"),
                        rs.getString("fecha"),
                        rs.getString("tipo"),
                        rs.getString("categoria"),
                        rs.getString("descripcion"),
                        rs.getDouble("monto")
                ));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error al consultar movimientos");
            System.err.println(e.toString());
        }
        return lista;
    }

    public void cargarTabla(Connection conexion, DefaultTableModel modelo) {
        modelo.setRowCount(0);
        for (Movimiento movimiento : listar(conexion)) {
            modelo.addRow(new Object[]{
                movimiento.id,
                movimiento.fecha,
                movimiento.tipo,
                movimiento.categoria,
                movimiento.descripcion,
                movimiento.monto
            });
        }
    }
}

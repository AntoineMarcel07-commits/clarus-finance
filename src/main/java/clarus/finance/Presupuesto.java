package clarus.finance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Presupuesto {
    public int id;
    public String categoria;
    public double limite;

    public Presupuesto InfoPresupuesto(int id, String categoria, double limite) {
        Presupuesto objPresupuesto = new Presupuesto();
        objPresupuesto.id = id;
        objPresupuesto.categoria = categoria;
        objPresupuesto.limite = limite;
        return objPresupuesto;
    }

    public boolean guardar(Connection conexion, Presupuesto presupuesto) {
        String sql = "INSERT INTO presupuestos(categoria, limite) VALUES (?, ?) "
                + "ON CONFLICT (categoria) DO UPDATE SET limite=EXCLUDED.limite";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, presupuesto.categoria);
            ps.setDouble(2, presupuesto.limite);
            int filas = ps.executeUpdate();
            ps.close();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al guardar presupuesto: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(Connection conexion, int idPresupuesto) {
        String sql = "DELETE FROM presupuestos WHERE id=?";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, idPresupuesto);
            int filas = ps.executeUpdate();
            ps.close();
            return filas > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar presupuesto: " + e.getMessage());
            return false;
        }
    }

    public ArrayList<Presupuesto> listar(Connection conexion) {
        ArrayList<Presupuesto> lista = new ArrayList<>();
        String sql = "SELECT id, categoria, limite FROM presupuestos ORDER BY categoria";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Presupuesto presupuesto = InfoPresupuesto(
                        rs.getInt("id"),
                        rs.getString("categoria"),
                        rs.getDouble("limite"));
                lista.add(presupuesto);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.out.println("Error al consultar presupuestos: " + e.getMessage());
        }

        return lista;
    }
}

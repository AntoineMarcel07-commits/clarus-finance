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
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.id = id;
        presupuesto.categoria = categoria;
        presupuesto.limite = limite;
        return presupuesto;
    }

    public boolean guardar(Connection conexion, Presupuesto presupuesto) {
        String query = "INSERT INTO presupuestos(categoria, limite) VALUES (?, ?) "
                + "ON CONFLICT (categoria) DO UPDATE SET limite = EXCLUDED.limite";
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ps.setString(1, presupuesto.categoria);
            ps.setDouble(2, presupuesto.limite);
            int filas = ps.executeUpdate();
            ps.close();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar presupuesto");
            System.err.println(e.toString());
            return false;
        }
    }

    public boolean eliminar(Connection conexion, int idPresupuesto) {
        try {
            PreparedStatement ps = conexion.prepareStatement(
                    "DELETE FROM presupuestos WHERE id_presupuesto = ?");
            ps.setInt(1, idPresupuesto);
            int filas = ps.executeUpdate();
            ps.close();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar presupuesto");
            System.err.println(e.toString());
            return false;
        }
    }

    public ArrayList<Presupuesto> listar(Connection conexion) {
        ArrayList<Presupuesto> lista = new ArrayList<>();
        String query = "SELECT id_presupuesto, categoria, limite "
                + "FROM presupuestos ORDER BY categoria";
        try {
            PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(InfoPresupuesto(
                        rs.getInt("id_presupuesto"),
                        rs.getString("categoria"),
                        rs.getDouble("limite")
                ));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error al consultar presupuestos");
            System.err.println(e.toString());
        }
        return lista;
    }
}

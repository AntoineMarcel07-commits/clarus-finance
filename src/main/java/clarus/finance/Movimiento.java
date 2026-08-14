package clarus.finance;

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
}

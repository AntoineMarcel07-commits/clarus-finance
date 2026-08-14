package clarus.finance;

import Interfaces.VentanaPrincipal;

public class ClarusFinance {
    public static Finanzas objFinanzas;
    public static VentanaPrincipal objVentana;

    public static void main(String[] args) {
        objFinanzas = new Finanzas();
        objVentana = new VentanaPrincipal(objFinanzas);
        objVentana.setVisible(true);
    }
}

package com.clarusfinance.ui.components;

import java.awt.Component;
import javax.swing.JOptionPane;

public final class UiMessages {
    private UiMessages() {
    }

    public static void error(Component parent, Throwable throwable) {
        Throwable relevant = throwable;
        while (relevant.getCause() != null && relevant.getMessage() == null) {
            relevant = relevant.getCause();
        }
        String message = relevant.getMessage() == null ? "Ocurrió un error inesperado" : relevant.getMessage();
        JOptionPane.showMessageDialog(parent, message, "Clarus Finance", JOptionPane.ERROR_MESSAGE);
    }

    public static void info(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Clarus Finance", JOptionPane.INFORMATION_MESSAGE);
    }
}

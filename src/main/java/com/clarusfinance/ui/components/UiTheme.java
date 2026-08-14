package com.clarusfinance.ui.components;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.border.Border;

public final class UiTheme {
    public static final Color NAVY = new Color(20, 35, 64);
    public static final Color NAVY_LIGHT = new Color(34, 55, 94);
    public static final Color BLUE = new Color(37, 99, 235);
    public static final Color BLUE_HOVER = new Color(29, 78, 216);
    public static final Color BACKGROUND = new Color(244, 247, 251);
    public static final Color SURFACE = Color.WHITE;
    public static final Color TEXT = new Color(30, 41, 59);
    public static final Color MUTED = new Color(100, 116, 139);
    public static final Color GREEN = new Color(5, 150, 105);
    public static final Color RED = new Color(220, 38, 38);
    public static final Color AMBER = new Color(217, 119, 6);
    public static final Color BORDER = new Color(226, 232, 240);
    public static final Font FONT = new Font("SansSerif", Font.PLAIN, 14);
    public static final Border CARD_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER),
            BorderFactory.createEmptyBorder(18, 18, 18, 18));

    private UiTheme() {
    }

    public static void install() {
        UIManager.put("Label.font", FONT);
        UIManager.put("Button.font", FONT.deriveFont(Font.BOLD));
        UIManager.put("TextField.font", FONT);
        UIManager.put("PasswordField.font", FONT);
        UIManager.put("ComboBox.font", FONT);
        UIManager.put("Table.font", FONT);
        UIManager.put("TableHeader.font", FONT.deriveFont(Font.BOLD));
        UIManager.put("Table.rowHeight", 32);
        UIManager.put("OptionPane.messageFont", FONT);
        UIManager.put("OptionPane.buttonFont", FONT.deriveFont(Font.BOLD));
    }
}

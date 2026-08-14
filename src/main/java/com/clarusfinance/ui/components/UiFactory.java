package com.clarusfinance.ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.text.NumberFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;

public final class UiFactory {
    private static final NumberFormat CURRENCY = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-MX"));
    private static final DateTimeFormatter MONTH_FORMAT = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("es", "MX"));

    private UiFactory() {
    }

    public static JButton primaryButton(String text) {
        return button(text, UiTheme.BLUE, Color.WHITE);
    }

    public static JButton secondaryButton(String text) {
        JButton button = button(text, Color.WHITE, UiTheme.TEXT);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UiTheme.BORDER),
                BorderFactory.createEmptyBorder(9, 15, 9, 15)));
        return button;
    }

    public static JButton dangerButton(String text) {
        return button(text, new Color(254, 242, 242), UiTheme.RED);
    }

    public static JButton navButton(String text) {
        JButton button = button(text, UiTheme.NAVY, Color.WHITE);
        button.setHorizontalAlignment(JButton.LEFT);
        button.setMaximumSize(new Dimension(210, 46));
        button.setPreferredSize(new Dimension(210, 46));
        return button;
    }

    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(UiTheme.TEXT);
        label.setFont(UiTheme.FONT.deriveFont(Font.BOLD, 25f));
        return label;
    }

    public static JLabel muted(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(UiTheme.MUTED);
        return label;
    }

    public static String money(Number number) {
        return CURRENCY.format(number);
    }

    public static String month(YearMonth period) {
        String formatted = period.format(MONTH_FORMAT);
        return Character.toUpperCase(formatted.charAt(0)) + formatted.substring(1);
    }

    private static JButton button(String text, Color background, Color foreground) {
        JButton button = new JButton(text);
        button.setBackground(background);
        button.setForeground(foreground);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
}

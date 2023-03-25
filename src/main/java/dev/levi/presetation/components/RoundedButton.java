package dev.levi.presetation.components;

import com.formdev.flatlaf.ui.FlatTextBorder;

import java.awt.*;
import javax.swing.JButton;

public class RoundedButton extends JButton {

    public RoundedButton(String text) {
        super(text);
        setOpaque(false);
        setBorder(null);
        setPreferredSize(new Dimension(100, 30));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        super.paintComponent(g);
    }

    @Override
    public void setContentAreaFilled(boolean b) {
    }

    @Override
    public void setBorderPainted(boolean b) {
    }

    @Override
    public void setOpaque(boolean b) {
    }
}


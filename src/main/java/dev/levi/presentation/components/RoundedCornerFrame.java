package dev.levi.presentation.components;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class RoundedCornerFrame extends JFrame {
    public RoundedCornerFrame(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(true);
     //   setBackground(new Color(0, 0, 0, 0));

        // Create a round rectangle shape with 20 pixel corner radius
        Shape shape = new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20);
        setShape(shape);

        // Draw the rounded rectangle onto the content pane
        JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        setContentPane(contentPane);

        // Set the frame to be translucent
        setOpacity(0.8f);
    }

}

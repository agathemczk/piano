package com.pianoo.view;

import javax.swing.*;
import java.awt.*;

public class RoundCloseButton extends JButton {

    public RoundCloseButton() {
        setPreferredSize(new Dimension(24, 24));
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setToolTipText("Fermer");
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Anti-aliasing pour courbes lisses
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond rouge en rond
        g2.setColor(Color.RED);
        g2.fillOval(0, 0, getWidth(), getHeight());

        // Croix blanche
        g2.setStroke(new BasicStroke(2f));
        g2.setColor(Color.WHITE);
        int pad = 6;
        g2.drawLine(pad, pad, getWidth() - pad, getHeight() - pad);
        g2.drawLine(getWidth() - pad, pad, pad, getHeight() - pad);

        g2.dispose();
    }
}
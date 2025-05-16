package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RoundNoteButton extends JButton {
    private Color backgroundColor;

    public RoundNoteButton(String text, Color bgColor) {
        super(text);
        this.backgroundColor = bgColor;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setPreferredSize(new Dimension(100, 100)); // Default size, can be overridden
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) {
            g2.setColor(backgroundColor.darker());
        } else if (getModel().isRollover()) {
            g2.setColor(backgroundColor.brighter());
        } else {
            g2.setColor(backgroundColor);
        }

        int diameter = Math.min(getWidth(), getHeight());
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;
        g2.fillOval(x, y, diameter, diameter);

        FontMetrics fm = g2.getFontMetrics();
        // Ensure text is not null or empty to avoid issues with getStringBounds
        String textToDraw = getText();
        if (textToDraw == null) {
            textToDraw = "";
        }
        Rectangle stringBounds = fm.getStringBounds(textToDraw, g2).getBounds();
        int textX = (getWidth() - stringBounds.width) / 2;
        int textY = (getHeight() - stringBounds.height) / 2 + fm.getAscent();
        g2.setColor(getForeground());
        g2.drawString(textToDraw, textX, textY);

        g2.dispose();
    }

    @Override
    public boolean contains(int xCoord, int yCoord) {
        int diameter = Math.min(getWidth(), getHeight());
        int radius = diameter / 2;
        int circleCenterX = getWidth() / 2;
        int circleCenterY = getHeight() / 2;

        return Point.distance(xCoord, yCoord, circleCenterX, circleCenterY) < radius;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }
}
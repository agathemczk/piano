package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class RoundCloseButton extends JPanel implements IRoundCloseButton {
    private IMenuNavigationListener listener;
    private boolean isHovered = false;

    public RoundCloseButton() {
        setPreferredSize(new Dimension(30, 30)); // Taille du bouton
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Ajouter un MouseListener pour détecter les clics
        addMouseListener(new MouseAdapter() {


            @Override
            public void mouseClicked(MouseEvent e) {
                if (listener != null) {

                    listener.onReturnMainMenu();
                } else {
                    System.out.println("Listener non défini !");
                }
            }
        });
    }

    public void setListener(IMenuNavigationListener listener) {
        this.listener = listener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dessiner le cercle rouge (ou plus clair si survolé)
        g2.setColor(isHovered ? Color.PINK : Color.RED);
        g2.fill(new Ellipse2D.Double(0, 0, getWidth(), getHeight()));

        // Dessiner la croix blanche
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        int padding = 8;
        g2.drawLine(padding, padding, getWidth() - padding, getHeight() - padding);
        g2.drawLine(getWidth() - padding, padding, padding, getHeight() - padding);

        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        return new Ellipse2D.Double(0, 0, getWidth(), getHeight()).contains(x, y);
    }
}
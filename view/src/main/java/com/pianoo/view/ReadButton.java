package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ReadButton extends JComponent {

    private boolean isPlaying = false;
    private OnClickListener listener;

    public ReadButton() {
        setPreferredSize(new Dimension(80, 40));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isPlaying = !isPlaying;
                if (listener != null) {
                    listener.onClick();
                }
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                repaint();
            }
        });
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
        repaint();
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dessiner le rectangle de fond
        int width = getWidth();
        int height = getHeight();

        // Couleur de fond - toujours bleue
        Color bgColor = new Color(0, 100, 200);

        // Vérifier si la souris est sur le bouton
        Point mousePosition = getMousePosition();
        boolean mouseOver = mousePosition != null;

        // Ajuster la couleur si la souris est au-dessus ou si lecture active
        if (mouseOver) {
            bgColor = bgColor.brighter();
        }

        // Dessiner le fond du bouton
        g2d.setColor(bgColor);
        g2d.fillRoundRect(2, 2, width - 4, height - 4, 8, 8);

        // Bordure
        g2d.setColor(new Color(40, 40, 40));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(2, 2, width - 4, height - 4, 8, 8);

        // Dessiner le texte "READ"
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "READ";
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int x = (width - textWidth) / 2;
        int y = (height - textHeight) / 2 + fm.getAscent();
        g2d.drawString(text, x, y);

        g2d.dispose();
    }

    // Interface pour le gestionnaire d'événements
    public interface OnClickListener {
        void onClick();
    }
}
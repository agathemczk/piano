package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayCatButton extends JPanel implements IPlayCatButton {

    private ICatListener catListener;
    private boolean isHovered = false;

    public PlayCatButton() {
        setPreferredSize(new Dimension(120, 40));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (catListener != null) {
                    catListener.onPlayCat();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });
    }

    public void setCatPlayListener(ICatListener catListener) {
        this.catListener = catListener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Couleur de fond - rose clair
        Color bgColor = new Color(255, 182, 193);

        // Ajuster la couleur si la souris est au-dessus
        if (isHovered) {
            bgColor = bgColor.brighter();
        }

        // Dessiner le fond du bouton
        g2d.setColor(bgColor);
        g2d.fillRoundRect(2, 2, width - 4, height - 4, 10, 10);

        // Bordure
        g2d.setColor(new Color(199, 21, 133));
        g2d.setStroke(new BasicStroke(2));
        g2d.drawRoundRect(2, 2, width - 4, height - 4, 10, 10);

        // Dessiner le texte "MEOW"
        g2d.setColor(new Color(75, 0, 130));
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        FontMetrics fm = g2d.getFontMetrics();
        String text = "MEOW";
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int x = (width - textWidth) / 2;
        int y = (height - textHeight) / 2 + fm.getAscent();
        g2d.drawString(text, x, y);

        g2d.dispose();
    }

}
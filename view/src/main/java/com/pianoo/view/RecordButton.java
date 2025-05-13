// view/src/main/java/com/pianoo/view/RecordButton.java
package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RecordButton extends JPanel {
    private boolean isRecording = false; // This field remains for visual state
    private Runnable onClickListener;

    public RecordButton() {
        setPreferredSize(new Dimension(80, 40));
        setOpaque(false);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (onClickListener != null) {
                    onClickListener.run();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
                // repaint(); // Repainting on hover can be kept if desired
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                // repaint(); // Repainting on hover can be kept if desired
            }
        });
    }

    public void setOnClickListener(Runnable listener) {
        this.onClickListener = listener;
    }

    public boolean isRecording() {
        return isRecording;
    }

    public void setVisualRecordingState(boolean state) {
        if (this.isRecording != state) {
            this.isRecording = state;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Fond du bouton
        g2d.setColor(isRecording ? Color.RED : Color.LIGHT_GRAY); // Visual state based on isRecording field
        g2d.fillRoundRect(2, 2, width - 4, height - 4, 8, 8);

        // Bordure
        g2d.setColor(new Color(40, 40, 40));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(2, 2, width - 4, height - 4, 8, 8);

        // Texte "REC"
        g2d.setColor(isRecording ? Color.WHITE : Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));

        FontMetrics fm = g2d.getFontMetrics();
        String text = "REC";
        int textWidth = fm.stringWidth(text);
        // int textHeight = fm.getHeight(); // Not strictly needed for centering y here
        int x = (width - textWidth) / 2;
        int y = (height - fm.getAscent() - fm.getDescent()) / 2 + fm.getAscent(); // Better y-centering for text

        g2d.drawString(text, x, y);
        g2d.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(80, 40);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
}

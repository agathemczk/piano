package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RecordButton extends JPanel implements IRecordButton{
    private boolean isRecording = false;
    private Runnable onClickListener;
    private int width = 80;
    private int height = 40;

    public RecordButton() {
        setPreferredSize(new Dimension(width, height));
        setOpaque(true);
        setBackground(Color.LIGHT_GRAY);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                toggleRecording();
                if (onClickListener != null) {
                    onClickListener.run();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    public void setOnClickListener(Runnable listener) {
        this.onClickListener = listener;
    }

    public boolean isRecording() {
        return isRecording;
    }

    private void toggleRecording() {
        isRecording = !isRecording;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fond du bouton
        g2d.setColor(isRecording ? Color.RED : Color.LIGHT_GRAY);
        g2d.fillRoundRect(0, 0, width - 1, height - 1, 10, 10);

        // Bordure
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(0, 0, width - 1, height - 1, 10, 10);

        // Texte "REC"
        g2d.setColor(isRecording ? Color.WHITE : Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 14));

        FontMetrics fm = g2d.getFontMetrics();
        String text = "REC";
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        g2d.drawString(text, (width - textWidth) / 2, ((height - textHeight) / 2) + fm.getAscent());
        g2d.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
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
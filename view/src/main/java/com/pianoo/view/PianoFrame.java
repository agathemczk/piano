package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PianoFrame extends JPanel implements IPianoFrame {

    private final JPanel pianoPanel;
    private final JComboBox<Integer> octaveSelector;
    private final int WHITE_KEYS_PER_OCTAVE = 7;

    public PianoFrame() {
        setSize(800, 600);
        setLayout(new BorderLayout());

        // Partie haute : croix rouge + sélection d’octave
        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel redX = new JLabel("✖");
        redX.setForeground(Color.RED);
        redX.setHorizontalAlignment(SwingConstants.RIGHT);
        redX.setCursor(new Cursor(Cursor.HAND_CURSOR));
        redX.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Container parent = SwingUtilities.getWindowAncestor(PianoFrame.this);
                if (parent instanceof JFrame frame) {
                    frame.setContentPane(new MainMenu()); // Revenir au menu
                    frame.revalidate();
                    frame.repaint();
                }
            }
        });

        topPanel.add(redX, BorderLayout.EAST);

        Integer[] octaves = new Integer[]{2, 3, 4, 5, 6, 7};
        octaveSelector = new JComboBox<>(octaves);
        octaveSelector.setSelectedItem(3);
        topPanel.add(octaveSelector, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        // Piano graphique
        pianoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawPiano(g, (int) octaveSelector.getSelectedItem());
            }
        };
        pianoPanel.setFocusable(true);
        add(pianoPanel, BorderLayout.CENTER);

        octaveSelector.addActionListener(e -> pianoPanel.repaint());

        setFocusable(true);
        requestFocusInWindow();
    }

    private void drawPiano(Graphics g, int octaves) {
        int totalWhiteKeys = WHITE_KEYS_PER_OCTAVE * octaves;
        int whiteKeyWidth = getWidth() / totalWhiteKeys;
        int whiteKeyHeight = getHeight();

        for (int i = 0; i < totalWhiteKeys; i++) {
            g.setColor(Color.WHITE);
            g.fillRect(i * whiteKeyWidth, 0, whiteKeyWidth, whiteKeyHeight);
            g.setColor(Color.BLACK);
            g.drawRect(i * whiteKeyWidth, 0, whiteKeyWidth, whiteKeyHeight);
        }

        int[] blackKeyPositions = {0, 1, 3, 4, 5};
        for (int o = 0; o < octaves; o++) {
            for (int i : blackKeyPositions) {
                int x = (o * WHITE_KEYS_PER_OCTAVE + i + 1) * whiteKeyWidth - (whiteKeyWidth / 4);
                int width = whiteKeyWidth / 2;
                int height = (int) (whiteKeyHeight * 0.6);
                g.setColor(Color.BLACK);
                g.fillRect(x, 0, width, height);
            }
        }
    }

    // === Interface IPianoFrame ===
    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public void addKeyListenerToFrame(KeyListener listener) {
        this.addKeyListener(listener);
        pianoPanel.addKeyListener(listener);
    }

    @Override
    public void setKeyListener(com.pianoo.controller.IController controller) {
        // plus utilisé
    }

    @Override
    public int getSelectedOctave() {
        return (int) octaveSelector.getSelectedItem();
    }
}

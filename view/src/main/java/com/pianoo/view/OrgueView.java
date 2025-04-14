package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class OrgueView extends JPanel {

    private final int WHITE_KEYS_PER_OCTAVE = 7;
    private final int OCTAVE_COUNT = 5;
    private final int TOTAL_WHITE_KEYS = WHITE_KEYS_PER_OCTAVE * OCTAVE_COUNT;

    private final Set<Integer> BLACK_KEY_OFFSETS = Set.of(1, 3, 6, 8, 10);
    private final String[] WHITE_KEY_NAMES = {"C", "D", "E", "F", "G", "A", "B"};

    private final java.util.List<PianoKey> keys = new ArrayList<>();

    public OrgueView() {
        setBackground(Color.DARK_GRAY);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                for (PianoKey key : keys) {
                    if (key.contains(e.getPoint())) {
                        System.out.println("Touche cliquée : " + key.note + " (" + (key.isBlack ? "noire" : "blanche") + ")");
                        break;
                    }
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        keys.clear();

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int margin = 20;
        int buttonHeight = 40;
        int availableHeight = panelHeight - buttonHeight - (3 * margin);

        int availableWidth = panelWidth - 2 * margin;
        int keyWidth = availableWidth / TOTAL_WHITE_KEYS;
        int keyHeight = availableHeight / 2;
        int blackKeyWidth = (int) (keyWidth * 0.66);
        int blackKeyHeight = (int) (keyHeight * 0.66);

        // Clavier supérieur
        int y1 = buttonHeight + margin;
        drawKeyboard(g, margin, y1, keyWidth, keyHeight, blackKeyWidth, blackKeyHeight);

        // Clavier inférieur
        int y2 = y1 + keyHeight + margin;
        drawKeyboard(g, margin, y2, keyWidth, keyHeight, blackKeyWidth, blackKeyHeight);
    }

    private void drawKeyboard(Graphics g, int xOffset, int yOffset, int keyWidth, int keyHeight, int blackKeyWidth, int blackKeyHeight) {
        int currentWhite = 0;

        // Touches blanches
        for (int octave = 0; octave < OCTAVE_COUNT; octave++) {
            for (int i = 0; i < WHITE_KEYS_PER_OCTAVE; i++) {
                int x = xOffset + currentWhite * keyWidth;
                Rectangle rect = new Rectangle(x, yOffset, keyWidth, keyHeight);
                String note = WHITE_KEY_NAMES[i] + octave;
                keys.add(new PianoKey(rect, false, note));

                g.setColor(Color.WHITE);
                g.fillRect(rect.x, rect.y, rect.width, rect.height);
                g.setColor(Color.BLACK);
                g.drawRect(rect.x, rect.y, rect.width, rect.height);

                g.setFont(new Font("Arial", Font.PLAIN, 10));
                g.drawString(note, rect.x + keyWidth / 3, rect.y + keyHeight - 10);

                currentWhite++;
            }
        }

        // Touches noires
        currentWhite = 0;
        for (int octave = 0; octave < OCTAVE_COUNT; octave++) {
            for (int note = 0; note < 12; note++) {
                if (BLACK_KEY_OFFSETS.contains(note)) {
                    if (currentWhite >= TOTAL_WHITE_KEYS - 1) break;

                    int x = xOffset + currentWhite * keyWidth + (keyWidth - blackKeyWidth / 2);
                    Rectangle rect = new Rectangle(x, yOffset, blackKeyWidth, blackKeyHeight);
                    String noteName = "♯" + (currentWhite % 7) + octave;

                    keys.add(new PianoKey(rect, true, noteName));

                    g.setColor(Color.BLACK);
                    g.fillRect(rect.x, rect.y, rect.width, rect.height);
                }

                if (!BLACK_KEY_OFFSETS.contains(note)) {
                    currentWhite++;
                }
                if (currentWhite >= TOTAL_WHITE_KEYS) break;
            }
        }
    }

    private static class PianoKey {
        Rectangle bounds;
        boolean isBlack;
        String note;

        public PianoKey(Rectangle bounds, boolean isBlack, String note) {
            this.bounds = bounds;
            this.isBlack = isBlack;
            this.note = note;
        }

        public boolean contains(Point p) {
            return bounds.contains(p);
        }
    }

    // --- BOUTON FERMER ARRONDI ---
    private static class RoundCloseButton extends JButton {
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
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.RED);
            g2.fillOval(0, 0, getWidth(), getHeight());

            g2.setStroke(new BasicStroke(2f));
            g2.setColor(Color.WHITE);
            int pad = 6;
            g2.drawLine(pad, pad, getWidth() - pad, getHeight() - pad);
            g2.drawLine(getWidth() - pad, pad, pad, getHeight() - pad);

            g2.dispose();
        }
    }

    // --- MAIN ---
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setTitle("Orgue - Vue Paysage (2 claviers piano)");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 400);
            frame.setLocationRelativeTo(null);
            frame.setLayout(new BorderLayout());

            // Panel principal avec bouton en haut
            JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
            topPanel.setOpaque(false);
            RoundCloseButton closeButton = new RoundCloseButton();
            closeButton.addActionListener(e -> System.exit(0));
            topPanel.add(closeButton);

            OrgueView orgueView = new OrgueView();

            frame.add(topPanel, BorderLayout.NORTH);
            frame.add(orgueView, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}
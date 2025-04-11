package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class OrgueView extends JPanel {

    private final int WHITE_KEY_WIDTH = 30;
    private final int WHITE_KEY_HEIGHT = 120;
    private final int BLACK_KEY_WIDTH = 20;
    private final int BLACK_KEY_HEIGHT = 80;

    private final int OCTAVE_COUNT = 5;
    private final int WHITE_KEYS_PER_OCTAVE = 7;
    private final int TOTAL_WHITE_KEYS = OCTAVE_COUNT * WHITE_KEYS_PER_OCTAVE;

    private final Set<Integer> BLACK_KEY_OFFSETS = Set.of(1, 3, 6, 8, 10);
    private final String[] WHITE_KEY_NAMES = {"C", "D", "E", "F", "G", "A", "B"};

    private final java.util.List<PianoKey> keys = new ArrayList<>();

    public OrgueView() {
        setPreferredSize(new Dimension(1800, 800)); // La taille de la fenêtre pourra être redimensionnée
        setLayout(null); // Utilisation de null layout pour permettre un positionnement libre

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

        int width = getWidth();
        int height = getHeight();

        // Le haut du clavier commence juste après la bordure du panneau
        int yOffset = height / 4; // Déplacer le clavier vers le bas pour qu'il ait un peu d'espace en haut

        // Laisser un espace entre les deux claviers, et les adapter à la largeur
        int keyboardWidth = width - 40; // Espacement de 20px de chaque côté
        int xOffset = 20;

        drawKeyboard(g, xOffset, yOffset, keyboardWidth);
        drawKeyboard(g, xOffset, yOffset + WHITE_KEY_HEIGHT + 40, keyboardWidth); // Le second clavier est en dessous
    }

    private void drawKeyboard(Graphics g, int xOffset, int yOffset, int keyboardWidth) {
        int currentWhite = 0;

        // --- Étape 1 : Touches blanches
        for (int octave = 0; octave < OCTAVE_COUNT; octave++) {
            for (int i = 0; i < WHITE_KEYS_PER_OCTAVE; i++) {
                int x = xOffset + currentWhite * WHITE_KEY_WIDTH;
                Rectangle rect = new Rectangle(x, yOffset, WHITE_KEY_WIDTH, WHITE_KEY_HEIGHT);
                String note = WHITE_KEY_NAMES[i] + octave;
                keys.add(new PianoKey(rect, false, note));

                g.setColor(Color.WHITE);
                g.fillRect(rect.x, rect.y, rect.width, rect.height);
                g.setColor(Color.BLACK);
                g.drawRect(rect.x, rect.y, rect.width, rect.height);

                // Dessiner le nom de la note
                g.setFont(new Font("Arial", Font.PLAIN, 10));
                g.drawString(note, rect.x + 10, rect.y + rect.height - 10);

                currentWhite++;
            }
        }

        // --- Étape 2 : Touches noires
        currentWhite = 0;
        for (int octave = 0; octave < OCTAVE_COUNT; octave++) {
            for (int note = 0; note < 12; note++) {
                if (BLACK_KEY_OFFSETS.contains(note)) {
                    if (currentWhite >= TOTAL_WHITE_KEYS - 1) break;

                    int x = xOffset + currentWhite * WHITE_KEY_WIDTH + (WHITE_KEY_WIDTH - BLACK_KEY_WIDTH / 2);
                    Rectangle rect = new Rectangle(x, yOffset, BLACK_KEY_WIDTH, BLACK_KEY_HEIGHT);
                    String noteName = "♯" + (currentWhite % 7) + octave; // Temporaire

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

    // Test
    public static void main(String[] args) {
        JFrame frame = new JFrame("Orgue 5 octaves interactif");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Créer la vue de l'orgue
        OrgueView orgueView = new OrgueView();
        frame.add(orgueView, BorderLayout.CENTER);

        // Ajuster la taille et rendre la fenêtre visible
        frame.setSize(1150, 500); // Définir la taille initiale de la fenêtre
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
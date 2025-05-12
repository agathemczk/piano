package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OrganFrame extends JPanel implements IOrganFrame {

    private IMenuNavigationListener listener; // Ajout du champ pour stocker le contrôleur
    private final int WHITE_KEYS_PER_OCTAVE = 7;
    private final int OCTAVE_COUNT = 5;
    private final int TOTAL_WHITE_KEYS = WHITE_KEYS_PER_OCTAVE * OCTAVE_COUNT;

    private final Set<Integer> BLACK_KEY_OFFSETS = Set.of(1, 3, 6, 8, 10);
    private final String[] WHITE_KEY_NAMES = {"C", "D", "E", "F", "G", "A", "B"};

    private final java.util.List<PianoKey> keys = new ArrayList<>();

    // Constructeur de la classe OrganFrame
    public OrganFrame() {
        setLayout(new BorderLayout());

        // Création du panneau supérieur
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        // Dans le constructeur XylophoneFrame, après la création de topPanel

// Panneau principal pour les boutons avec BorderLayout
        JPanel buttonPanel = new JPanel(new BorderLayout(10, 0));
        buttonPanel.setBackground(new Color(230, 230, 230));
        buttonPanel.setOpaque(true);

// Créer le bouton d'enregistrement
        RecordButton recordButton = new RecordButton();
        recordButton.setOnClickListener(() -> {
            boolean isRecording = recordButton.isRecording();
            System.out.println("Enregistrement: " + (isRecording ? "activé" : "désactivé"));
            System.out.println("reliage au controller prochainement");
        });

// Sous-panneau central pour centrer le bouton d'enregistrement
        JPanel recordButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        recordButtonPanel.setOpaque(false);
        recordButtonPanel.add(recordButton);

// Bouton de retour au menu principal
        RoundCloseButton closeButton = new RoundCloseButton();
        closeButton.setListener(() -> {
            if (listener != null) {
                listener.onReturnMainMenu();
            }
        });

// Panneau pour le bouton de fermeture
        JPanel closeButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closeButtonPanel.setOpaque(false);
        closeButtonPanel.add(closeButton);

// Ajouter les panneaux au panneau principal
        buttonPanel.add(recordButtonPanel, BorderLayout.CENTER);
        buttonPanel.add(closeButtonPanel, BorderLayout.EAST);

// Ajouter le panneau de boutons au panneau supérieur
        topPanel.add(buttonPanel, BorderLayout.CENTER);

// Ajouter le panneau supérieur au conteneur principal
        add(topPanel, BorderLayout.NORTH);

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

    // Méthode pour définir le listener
    public void setListener(IMenuNavigationListener listener) {
        this.listener = listener;
    }

    // Méthode pour dessiner le clavier
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

    // Méthode pour dessiner le clavier
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

    // Classe pour représenter les touches du clavier
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

    @Override
    public JPanel getPanel() {
        return this;
    }
}
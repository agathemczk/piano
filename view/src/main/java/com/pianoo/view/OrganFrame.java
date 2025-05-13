package com.pianoo.view;

import com.pianoo.controller.IController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class OrganFrame extends JPanel implements IOrganFrame, KeyListener {

    private IMenuNavigationListener listener;
    private IController controller;

    private final int WHITE_KEYS_PER_OCTAVE = 7;
    private final int OCTAVE_COUNT = 5;
    private final int TOTAL_WHITE_KEYS = WHITE_KEYS_PER_OCTAVE * OCTAVE_COUNT;

    private final String[] WHITE_KEY_NAMES = {"C", "D", "E", "F", "G", "A", "B"};

    private final List<PianoKey> keys = new ArrayList<>();
    private Integer currentPlayingNote = null;

    // Constructeur de la classe OrganFrame
    public OrganFrame() {
        setLayout(new BorderLayout());

        // Création du panneau supérieur
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

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

        // Créer le bouton de lecture
        ReadButton readButton = new ReadButton();
        readButton.setOnClickListener(() -> {
            // boolean isPlaying = readButton.isPlaying(); // Plus forcément utile ici
            // System.out.println("Lecture: " + (isPlaying ? "activée" : "désactivée")); // Log ancien
            if (controller != null) {
                System.out.println("OrganFrame: ReadButton cliqué, appel de controller.onReadScoreRequested()");
                controller.onReadScoreRequested();
            } else {
                System.err.println("OrganFrame: Controller non initialisé lors du clic sur ReadButton.");
                JOptionPane.showMessageDialog(this, "Erreur : Le contrôleur n'est pas disponible pour lire la partition.", "Erreur Interne", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Sous-panneau central pour les boutons d'enregistrement et de lecture
        JPanel mediaButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        mediaButtonsPanel.setOpaque(false);
        mediaButtonsPanel.add(recordButton);
        mediaButtonsPanel.add(readButton);

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
        buttonPanel.add(mediaButtonsPanel, BorderLayout.CENTER);
        buttonPanel.add(closeButtonPanel, BorderLayout.EAST);

        // Ajouter le panneau de boutons au panneau supérieur
        topPanel.add(buttonPanel, BorderLayout.CENTER);

        // Ajouter le panneau supérieur au conteneur principal
        add(topPanel, BorderLayout.NORTH);

        // Configuration pour le clavier
        setFocusable(true);
        addKeyListener(this);

        // Configuration pour les clics de souris
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePress(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseRelease();
            }
        });
    }

    private void handleMousePress(MouseEvent e) {
        for (PianoKey key : keys) {
            if (key.contains(e.getPoint())) {
                boolean isUpperKeyboard = e.getY() < getHeight() / 2;
                String noteName = key.note;

                if (controller != null) {
                    // Calculer la note MIDI à partir du nom de la note
                    int baseMidiNote = controller.getMidiNoteFromKeyName(noteName);

                    // Ajuster pour le clavier supérieur/inférieur
                    int midiNote = controller.adjustMidiNoteForKeyboard(baseMidiNote, isUpperKeyboard);

                    if (midiNote != -1) {
                        controller.onOrganKeyPressed(midiNote);
                        currentPlayingNote = midiNote;
                        repaint();

                        System.out.println(noteName + " (MIDI: " + midiNote +
                                ") " + (isUpperKeyboard ? "supérieur" : "inférieur"));
                    }
                }
                break;
            }
        }
    }

    private void handleMouseRelease() {
        if (currentPlayingNote != null && controller != null) {
            controller.onOrganKeyReleased(currentPlayingNote);
            currentPlayingNote = null;
            repaint();
        }
    }

    // Méthode pour définir le listener
    @Override
    public void setListener(IMenuNavigationListener listener) {
        this.listener = listener;
    }

    @Override
    public void setController(IController controller) {
        this.controller = controller;
    }

    @Override
    public void setKeyListener(IController controller) {
        this.controller = controller;
    }

    // Implémentation des méthodes de l'interface KeyListener
    @Override
    public void keyTyped(KeyEvent e) {
        // Non utilisé
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (controller != null) {
            int midiNote = controller.getMidiNoteForKeyCode(e.getKeyCode());
            if (midiNote != -1) {
                controller.onOrganKeyPressed(midiNote);
                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (controller != null) {
            int midiNote = controller.getMidiNoteForKeyCode(e.getKeyCode());
            if (midiNote != -1) {
                controller.onOrganKeyReleased(midiNote);
                repaint();
            }
        }
    }

    // Méthode pour dessiner le clavier
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        keys.clear(); // Effacer la liste ici, une seule fois

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
        // Dessiner d'abord toutes les touches blanches
        int whiteKeyCount = 0;
        for (int octave = 0; octave < OCTAVE_COUNT; octave++) {
            for (int i = 0; i < WHITE_KEYS_PER_OCTAVE; i++) {
                int x = xOffset + whiteKeyCount * keyWidth;
                Rectangle rect = new Rectangle(x, yOffset, keyWidth, keyHeight);
                String note = WHITE_KEY_NAMES[i] + octave;
                PianoKey pianoKey = new PianoKey(rect, false, note);
                keys.add(pianoKey);

                // Déterminer si cette touche est activée
                boolean isUpperKey = yOffset < getHeight()/2;
                boolean isActive = false;

                if (controller != null) {
                    // Obtenir la note MIDI du contrôleur
                    int baseMidiNote = controller.getMidiNoteFromKeyName(note);
                    int midiNote = controller.adjustMidiNoteForKeyboard(baseMidiNote, isUpperKey);
                    isActive = controller.isNoteActive(midiNote);
                }

                // Couleur pour les touches blanches
                if (isActive) {
                    g.setColor(new Color(173, 216, 230)); // Bleu clair quand pressée
                } else {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(rect.x, rect.y, rect.width, rect.height);
                g.setColor(Color.BLACK);
                g.drawRect(rect.x, rect.y, rect.width, rect.height);

                g.setFont(new Font("Arial", Font.PLAIN, 10));
                g.drawString(note, rect.x + keyWidth / 3, rect.y + keyHeight - 10);

                whiteKeyCount++;
            }
        }

        // Dessiner ensuite toutes les touches noires (pour qu'elles apparaissent au-dessus)
        whiteKeyCount = 0;
        for (int octave = 0; octave < OCTAVE_COUNT; octave++) {
            for (int i = 0; i < WHITE_KEYS_PER_OCTAVE; i++) {
                // Vérifier si cette position a une touche noire (après Do, Ré, Fa, Sol, La)
                boolean hasBlackKey = i == 0 || i == 1 || i == 3 || i == 4 || i == 5;

                if (hasBlackKey && whiteKeyCount < TOTAL_WHITE_KEYS - 1) {
                    int x = xOffset + whiteKeyCount * keyWidth + (keyWidth - blackKeyWidth / 2);
                    Rectangle rect = new Rectangle(x, yOffset, blackKeyWidth, blackKeyHeight);

                    // Nommer correctement les touches noires
                    String noteName;
                    switch (i) {
                        case 0: noteName = "C#" + octave; break;  // Do#
                        case 1: noteName = "D#" + octave; break;  // Ré#
                        case 3: noteName = "F#" + octave; break;  // Fa#
                        case 4: noteName = "G#" + octave; break;  // Sol#
                        case 5: noteName = "A#" + octave; break;  // La#
                        default: noteName = "X#" + octave; break; // Ne devrait jamais arriver
                    }

                    PianoKey pianoKey = new PianoKey(rect, true, noteName);

                    // Ajouter les touches noires AU DÉBUT pour la détection
                    keys.add(0, pianoKey);

                    // Déterminer si cette touche est activée
                    boolean isUpperKey = yOffset < getHeight()/2;
                    boolean isActive = false;

                    if (controller != null) {
                        // Obtenir la note MIDI du contrôleur
                        int baseMidiNote = controller.getMidiNoteFromKeyName(noteName);
                        int midiNote = controller.adjustMidiNoteForKeyboard(baseMidiNote, isUpperKey);
                        isActive = controller.isNoteActive(midiNote);
                    }

                    // Couleur pour les touches noires
                    if (isActive) {
                        g.setColor(new Color(100, 100, 180)); // Bleu foncé quand pressée
                    } else {
                        g.setColor(Color.BLACK);
                    }

                    g.fillRect(rect.x, rect.y, rect.width, rect.height);
                }

                whiteKeyCount++;
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
package com.pianoo.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PianoFrame extends JPanel implements IPianoFrame, KeyListener {

    private final JPanel pianoPanel;
    private IMenuNavigationListener listener;
    private final JComboBox<Integer> octaveSelector;
    private final int WHITE_KEYS_PER_OCTAVE = 7;

    public PianoFrame() {
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

// Sélecteur d'octaves à gauche
        Integer[] octaves = new Integer[]{2, 3, 4, 5, 6, 7};
        octaveSelector = new JComboBox<>(octaves);
        octaveSelector.setSelectedItem(3);
        topPanel.add(octaveSelector, BorderLayout.WEST);

// Bouton rond pour fermer à droite avec une marge
        // Bouton rond pour fermer à droite avec une marge
        RoundCloseButton closeButton = new RoundCloseButton();
        closeButton.setListener(() -> {
            if (listener != null) {
                listener.onReturnMainMenu(); // Notifie le contrôleur
            }
        });

// Ajouter le bouton au panneau supérieur
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

// Ajout du panneau supérieur
        add(topPanel, BorderLayout.NORTH);

        pianoPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawPiano(g, (int) octaveSelector.getSelectedItem());
            }
        };
        add(pianoPanel, BorderLayout.CENTER);

        octaveSelector.addActionListener(e -> pianoPanel.repaint());

        addKeyListener(this);
        setFocusable(true);
        pianoPanel.setFocusable(true);
    }

    public void setListener(IMenuNavigationListener listener) {
        this.listener = listener;
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

    // Gestion des touches
    @Override
    public void keyTyped(KeyEvent e) {
        // Optionnel
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = Character.toUpperCase(e.getKeyChar());

        switch (key) {
            case 'A':
                System.out.println("Do");
                break;
            case 'Z':
                System.out.println("Ré");
                break;
            case 'E':
                System.out.println("Mi");
                break;
            case 'R':
                System.out.println("Fa");
                break;
            case 'T':
                System.out.println("Sol");
                break;
            case 'Y':
                System.out.println("La");
                break;
            case 'U':
                System.out.println("Si");
                break;
            default:
                System.out.println("Touche non assignée : " + key);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Optionnel
    }

    @Override
    public JPanel getPanel() {
        return this; // Retourne l'instance actuelle de PianoFrame
    }

    @Override
    public void addKeyListenerToFrame(final KeyListener listener) {
        addKeyListener(listener); // Ajoute un KeyListener au panneau
    }

    @Override
    public void startPiano() {
        setVisible(true); // Affiche le panneau du piano
    }

    @Override
    public void stopPiano() {
        setVisible(false); // Cache le panneau du piano
    }
}
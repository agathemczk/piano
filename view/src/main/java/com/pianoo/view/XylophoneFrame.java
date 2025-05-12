package com.pianoo.view;

import com.pianoo.controller.IController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class XylophoneFrame extends JPanel implements IXylophoneFrame, KeyListener {

    private static final String[] NOTES = {"C", "D", "E", "F", "G", "A", "B"};
    private static final Color[] COLORS = {
            Color.RED, Color.ORANGE, Color.YELLOW,
            Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA
    };



    private final JPanel xylophonePanel;
    private IMenuNavigationListener listener;
    private IController controller;
    private final List<JButton> noteButtons = new ArrayList<>();

    public XylophoneFrame() {
        setLayout(new BorderLayout());
        setFocusable(true);
        requestFocusInWindow();

        // Haut : barre d'outils
        JPanel topPanel = createTopPanel();
        add(topPanel, BorderLayout.NORTH);

        // Centre : xylophone
        xylophonePanel = createXylophonePanel();
        add(xylophonePanel, BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        // Boutons média (enregistrement et lecture)
        JPanel mediaButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        mediaButtonsPanel.setOpaque(false);

        RecordButton recordButton = new RecordButton();
        recordButton.setOnClickListener(() -> {
            boolean isRecording = recordButton.isRecording();
            System.out.println("Enregistrement: " + (isRecording ? "activé" : "désactivé"));
        });

        ReadButton readButton = new ReadButton();
        readButton.setOnClickListener(() -> {
            boolean isPlaying = readButton.isPlaying();
            System.out.println("Lecture: " + (isPlaying ? "activée" : "désactivée"));
        });

        mediaButtonsPanel.add(recordButton);
        mediaButtonsPanel.add(readButton);

        // Bouton de fermeture
        RoundCloseButton closeButton = new RoundCloseButton();
        closeButton.setListener(() -> {
            if (listener != null) {
                listener.onReturnMainMenu();
            }
        });

        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closePanel.setOpaque(false);
        closePanel.add(closeButton);

        topPanel.add(mediaButtonsPanel, BorderLayout.CENTER);
        topPanel.add(closePanel, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createXylophonePanel() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        JPanel xylophonePanel = new JPanel();
        xylophonePanel.setLayout(new BoxLayout(xylophonePanel, BoxLayout.X_AXIS));
        xylophonePanel.setOpaque(false);

        int baseHeight = 250;
        int width = 90;

        // Création des touches du xylophone
        for (int i = 0; i < NOTES.length; i++) {
            JButton noteButton = new JButton(NOTES[i]);
            noteButton.setBackground(COLORS[i]);
            noteButton.setOpaque(true);
            noteButton.setBorderPainted(false);
            noteButton.setFont(new Font("Arial", Font.BOLD, 20));

            // Ajuster la hauteur pour simuler les barres du xylophone
            int buttonHeight = baseHeight - (i * 15);
            noteButton.setPreferredSize(new Dimension(width, buttonHeight));
            noteButton.setMaximumSize(new Dimension(width, buttonHeight));
            noteButton.setMinimumSize(new Dimension(width, buttonHeight));

            final int noteIndex = i;
            noteButton.addActionListener(e -> playNote(noteIndex));

            noteButtons.add(noteButton);

            JPanel wrapper = new JPanel();
            wrapper.setLayout(new BoxLayout(wrapper, BoxLayout.Y_AXIS));
            wrapper.setOpaque(false);
            wrapper.add(Box.createVerticalGlue());
            wrapper.add(noteButton);
            wrapper.add(Box.createVerticalGlue());

            xylophonePanel.add(wrapper);

            if (i < NOTES.length - 1) {
                xylophonePanel.add(Box.createRigidArea(new Dimension(5, 0)));
            }
        }

        centerPanel.add(xylophonePanel);
        return centerPanel;
    }

    private void playNote(int noteIndex) {
        System.out.println("Joue la note : " + NOTES[noteIndex]);
        if (controller != null) {
            controller.onNotePlayed(NOTES[noteIndex]);
        }
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public void addKeyListenerToFrame(KeyListener listener) {
        this.addKeyListener(listener);
        xylophonePanel.addKeyListener(listener);
    }

    @Override
    public void setKeyListener(final IController controller) {
        this.controller = controller;
    }

    @Override
    public void setController(final IController controller) {
        this.controller = controller;
    }

    @Override
    public void highlightNote(int note) {
        if (note >= 0 && note < noteButtons.size()) {
            noteButtons.get(note).setBackground(noteButtons.get(note).getBackground().darker());
        }
    }

    @Override
    public void resetNote(int note) {
        if (note >= 0 && note < noteButtons.size()) {
            noteButtons.get(note).setBackground(COLORS[note]);
        }
    }

    public void setListener(IMenuNavigationListener listener) {
        this.listener = listener;
    }

    @Override
    public void keyTyped(final KeyEvent e) {
    }

    @Override
    public void keyPressed(final KeyEvent e) {
    }

    @Override
    public void keyReleased(final KeyEvent e) {
    }
}
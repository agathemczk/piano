package com.pianoo.view;

import javax.swing.*;
import java.awt.*;

public class XylophoneFrame extends JPanel implements IXylophoneFrame {

    private IMenuNavigationListener listener;

    private static final String[] NOTES = {"C", "D", "E", "F", "G", "A", "B"};
    private static final Color[] COLORS = {
            Color.RED, Color.ORANGE, Color.YELLOW,
            Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA
    };

    public XylophoneFrame() {
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

        // ===== Xylophone centré =====
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        JPanel xylophonePanel = new JPanel();
        xylophonePanel.setLayout(new BoxLayout(xylophonePanel, BoxLayout.X_AXIS));
        xylophonePanel.setOpaque(false);

        int baseHeight = 250;
        int width = 90;

        for (int i = 0; i < NOTES.length; i++) {
            JButton noteButton = new JButton(NOTES[i]);
            noteButton.setBackground(COLORS[i]);
            noteButton.setOpaque(true);
            noteButton.setBorderPainted(false);
            noteButton.setFont(new Font("Arial", Font.BOLD, 20));

            int buttonHeight = baseHeight - (i * 15);
            noteButton.setPreferredSize(new Dimension(width, buttonHeight));
            noteButton.setMaximumSize(new Dimension(width, buttonHeight));
            noteButton.setMinimumSize(new Dimension(width, buttonHeight));

            final int index = i;
            noteButton.addActionListener(e -> playNote(NOTES[index]));

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
        add(centerPanel, BorderLayout.CENTER);
    }

    private void playNote(String note) {
        System.out.println("Joue la note : " + note);
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    public void setListener(IMenuNavigationListener listener) {
        this.listener = listener;
    }

}

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

        // Bouton rond pour fermer à droite
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

        // Ajout du panneau supérieur au frame
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
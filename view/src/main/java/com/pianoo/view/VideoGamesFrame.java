package com.pianoo.view;

import javax.swing.*;
import java.awt.*;

public class VideoGamesFrame extends InstrumentFrame implements IInstrumentFrame {

    private static final String[] NOTE_NAMES = {"C", "D", "E", "F", "G", "A", "B"};
    private static final Color[] NOTE_COLORS = {
            new Color(255, 100, 100), // C
            new Color(255, 180, 100), // D
            new Color(255, 255, 100), // E
            new Color(100, 255, 100), // F
            new Color(100, 200, 255), // G
            new Color(150, 100, 255), // A
            new Color(220, 100, 220)  // B
    };

    public VideoGamesFrame() {
        super();

        JPanel notesPanel = new JPanel(new GridBagLayout());
        notesPanel.setOpaque(false);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonsPanel.setOpaque(false);

        for (int i = 0; i < NOTE_NAMES.length; i++) {
            final String noteName = NOTE_NAMES[i];
            RoundNoteButton noteButton = new RoundNoteButton(noteName, NOTE_COLORS[i]);
            noteButton.setFont(new Font("Arial", Font.BOLD, 20));
            noteButton.setForeground(Color.WHITE);
            noteButton.setPreferredSize(new Dimension(120, 120));

            noteButton.addActionListener(e -> {
                System.out.println("Bouton pressé: " + noteName);
                if (controller != null) {
                    controller.onVideoGameNotePressed(noteName);
                }
            });
            buttonsPanel.add(noteButton);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        notesPanel.add(buttonsPanel, gbc);
        add(notesPanel, BorderLayout.CENTER);
    }
}
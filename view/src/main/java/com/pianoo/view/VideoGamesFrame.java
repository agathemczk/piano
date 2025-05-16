package com.pianoo.view;

import com.pianoo.controller.IController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VideoGamesFrame extends JPanel implements IVideoGamesFrame, IMenuNavigationListener {

    private IController controller;
    private IMenuNavigationListener menuNavigationListener;
    private TopPanel topPanel;
    private RecordButton recordButton;


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
        setLayout(new BorderLayout());

        // Remplacer FlowLayout par GridBagLayout pour un meilleur contrôle
        JPanel notesPanel = new JPanel(new GridBagLayout());
        notesPanel.setOpaque(false);

        // Créer un sous-panel pour contenir uniquement les boutons
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

        // Ajouter le panel de boutons au panel principal avec GridBagLayout
        // pour le centrer verticalement et horizontalement
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


    private void initializeTopPanel() {
        if (this.controller == null || (this.menuNavigationListener == null && !(this instanceof IMenuNavigationListener))) {
            return;
        }
        if (this.topPanel != null) return;

        IMenuNavigationListener actualListener = (this.menuNavigationListener != null) ? this.menuNavigationListener : this;
        this.topPanel = new TopPanel(this.controller, actualListener);

        // Récupérer la référence au bouton d'enregistrement
        this.recordButton = this.topPanel.getRecordButtonInstance();

        add(this.topPanel, BorderLayout.NORTH);
        revalidate();
        repaint();
    }

    @Override
    public JPanel getPanel() {
        return this;
    }

    @Override
    public void setListener(IMenuNavigationListener listener) {
        this.menuNavigationListener = listener;
        initializeTopPanel();
    }

    @Override
    public void setController(IController controller) {
        this.controller = controller;
        initializeTopPanel();
    }

    @Override
    public void onReturnMainMenu() {
        if (menuNavigationListener != null && menuNavigationListener != this) {
            menuNavigationListener.onReturnMainMenu();
        } else if (controller != null) {
            controller.onReturnMainMenu();
        }
    }

    public void updateRecordButtonState(boolean isRecording) {
        if (recordButton != null) {
            recordButton.setVisualRecordingState(isRecording);
        }
    }
}
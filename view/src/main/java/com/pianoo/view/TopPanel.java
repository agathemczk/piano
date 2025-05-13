package com.pianoo.view;

import com.pianoo.controller.IController;
import javax.swing.*;
import java.awt.*;

public class TopPanel extends JPanel {

    private RecordButton recordButton;
    private ReadButton readButton;
    private RoundCloseButton closeButton;
    private IController controller;
    private IMenuNavigationListener menuNavigationListener;

    public TopPanel(IController controller, IMenuNavigationListener menuNavigationListener) {
        this.controller = controller;
        this.menuNavigationListener = menuNavigationListener;
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setOpaque(false);

        // Panneau central pour les boutons média
        JPanel mediaButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        mediaButtonsPanel.setOpaque(false);

        recordButton = new RecordButton();
        recordButton.setOnClickListener(() -> {
            if (controller != null) {
                controller.toggleRecording();
            }
        });

        readButton = new ReadButton();
        readButton.setOnClickListener(() -> {
            // Logique pour le bouton de lecture (peut être étendue plus tard)
            boolean isPlaying = readButton.isPlaying(); // Suppose ReadButton a cette méthode
            System.out.println("Lecture: " + (isPlaying ? "activée" : "désactivée"));
            // if (controller != null) { controller.togglePlayback(); }
        });

        mediaButtonsPanel.add(recordButton);
        mediaButtonsPanel.add(readButton);

        // Panneau pour le bouton de fermeture à droite
        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closePanel.setOpaque(false);

        closeButton = new RoundCloseButton();
        closeButton.setListener(() -> {
            if (menuNavigationListener != null) {
                menuNavigationListener.onReturnMainMenu();
            }
        });
        closePanel.add(closeButton);

        add(mediaButtonsPanel, BorderLayout.CENTER);
        add(closePanel, BorderLayout.EAST);
    }

    public RecordButton getRecordButtonInstance() {
        return recordButton;
    }
}
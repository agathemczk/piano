package com.pianoo.view;

import javax.swing.*;
import java.awt.*;

public class CatFrame extends JPanel implements ICatFrame {

    private PlayCatButton playCatButton;
    private ICatListener catListener;
    private IMenuNavigationListener listener;

    public CatFrame() {
        // Définir un seul layout pour le panneau principal
        setLayout(new BorderLayout());
        setBackground(new Color(250, 240, 230));

        // === PARTIE HAUTE (barre de contrôle) ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);

        RoundCloseButton closeButton = new RoundCloseButton();
        closeButton.setListener(() -> {
            if (listener != null) {
                listener.onReturnMainMenu();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        // === PARTIE CENTRALE (bouton play) ===
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(250, 240, 230));

        // Création et ajout du bouton au centre
        playCatButton = new PlayCatButton();
        playCatButton.setPlayCatListener(() -> {
            if (catListener != null) {
                catListener.onPlayCat();
            }
        });

        centerPanel.add(playCatButton);
        add(centerPanel, BorderLayout.CENTER);
    }

    public void setListener(IMenuNavigationListener listener) {
        this.listener = listener;
    }

    @Override
    public void setCatPlayListener(ICatListener catListener) {
        this.catListener = catListener;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }
}
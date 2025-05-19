package com.pianoo.view;

import javax.swing.*;
import java.awt.*;

public class CatFrame extends InstrumentFrame implements ICatFrame {

    private PlayCatButton playCatButton;
    private ICatListener catListener;

    public CatFrame() {
        super();
        JPanel topPanelLocal = new JPanel(new BorderLayout());
        topPanelLocal.setOpaque(false);

        RoundCloseButton closeButton = new RoundCloseButton();
        closeButton.setListener(() -> {
            onReturnMainMenu();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        topPanelLocal.add(buttonPanel, BorderLayout.EAST);

        add(topPanelLocal, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        playCatButton = new PlayCatButton();
        playCatButton.setCatPlayListener(() -> {
            if (catListener != null) {
                catListener.onPlayCat();
            }
        });

        centerPanel.add(playCatButton);
        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void setCatPlayListener(ICatListener catListener) {
        this.catListener = catListener;
    }
}
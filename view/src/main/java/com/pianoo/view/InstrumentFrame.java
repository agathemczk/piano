package com.pianoo.view;

import com.pianoo.controller.IController;

import javax.swing.*;
import java.awt.*;

public abstract class InstrumentFrame extends JPanel implements IInstrumentFrame, IMenuNavigationListener {

    protected IController controller;
    protected IMenuNavigationListener menuNavigationListener;
    protected TopPanel topPanel;
    protected RecordButton recordButton;

    public InstrumentFrame() {
        setLayout(new BorderLayout()); // Layout commun, peut être surchargé si nécessaire
    }

    public JPanel getPanel() {
        return this;
    }

    public void setListener(IMenuNavigationListener listener) {
        this.menuNavigationListener = listener;
        // Assurer l'initialisation du TopPanel si le contrôleur est déjà défini
        if (this.controller != null) {
            initializeTopPanel();
        }
    }

    public void setController(IController controller) {
        this.controller = controller;
        // Assurer l'initialisation du TopPanel si le listener est déjà défini
        // ou si la frame elle-même est le listener
        if (this.menuNavigationListener != null || this instanceof IMenuNavigationListener) {
            initializeTopPanel();
        }
    }

    /**
     * Initialise le TopPanel. Peut être surchargée par les sous-classes
     * si elles n'ont pas de TopPanel standard ou ont une initialisation spécifique.
     */
    protected void initializeTopPanel() {

        if (this.topPanel == null && this.controller != null && (this.menuNavigationListener != null || this instanceof IMenuNavigationListener)) {
            IMenuNavigationListener actualListener = (this.menuNavigationListener != null && this.menuNavigationListener != this) ? this.menuNavigationListener : this;
            this.topPanel = new TopPanel(this.controller, actualListener);
            this.recordButton = this.topPanel.getRecordButtonInstance();
            add(this.topPanel, BorderLayout.NORTH);
            revalidate();
            repaint();
        }
    }

    public void updateRecordButtonState(boolean isRecording) {
        if (recordButton != null) {
            recordButton.setVisualRecordingState(isRecording);
        }
    }

    @Override
    public void onReturnMainMenu() {
        if (menuNavigationListener != null && menuNavigationListener != this) {
            menuNavigationListener.onReturnMainMenu();
        } else if (controller != null) {
            controller.showMainMenu();
        }
    }

    // Méthode pour la gestion spécifique du KeyListener si nécessaire par certaines frames.
    // Peut être laissée vide ici ou déclarée abstraite si toutes les frames doivent l'implémenter.
    // Pour l'instant, laissons la possibilité aux frames concernées de la surcharger.
    @Override
    public void setKeyListener(final IController controller) {
        this.controller = controller;
    }
}
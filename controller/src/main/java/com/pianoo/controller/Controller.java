package com.pianoo.controller;

import com.pianoo.model.IModel;
import com.pianoo.model.IVideoGames;
import com.pianoo.model.IXylophone;
import com.pianoo.view.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements IController, IOnChoiceSelectedListener, IMenuNavigationListener,  KeyListener {

    private final IMusicPlayer musicPlayer;;
    private IPianoFrame pianoFrame;
    private IOrganFrame organFrame;
    private IXylophoneFrame xylophoneFrame;
    private IVideoGamesFrame videoGamesFrame;
    private IRoundCloseButton roundCloseButton;
    private IPianoController pianoController;
    private IMainMenu mainMenu;

    private static final int KEYBOARD_OCTAVE = 4;

    public Controller(IMusicPlayer musicPlayer, IMainMenu mainMenu, IPianoFrame pianoFrame, IOrganFrame organFrame, IXylophoneFrame xylophoneFrame, IVideoGamesFrame videoGamesFrame, IRoundCloseButton roundCloseButton) {
        this.musicPlayer = musicPlayer;
        this.mainMenu = mainMenu;
        this.pianoFrame = pianoFrame;
        this.organFrame = organFrame;
        this.xylophoneFrame = xylophoneFrame;
        this.videoGamesFrame = videoGamesFrame;
        this.roundCloseButton = roundCloseButton;
        this.mainMenu.setInstrumentSelectedListener(this);
        this.mainMenu.setVisible(true);
        this.roundCloseButton.setListener(this);
        this.organFrame.setListener(this);
        this.pianoFrame.setListener(this);
        this.xylophoneFrame.setListener(this);
        this.videoGamesFrame.setListener(this);


    }

    @Override
    public void onInstrumentSelected(String instrumentName) {
        if ("Piano".equals(instrumentName)) {
            openPiano();
        }
        if ("Xylophone".equals(instrumentName)) {
            openXylophone();
        }
        if ("VideoGames".equals(instrumentName)) {
            openVideoGames();
        }
        if ("Organ".equals(instrumentName)) {
            openOrgan();
        }
        if ("Drums".equals(instrumentName)) {
            openDrums();
        }
    }

    @Override
    public void onReturnMainMenu() {
        openMainMenu();
    }

    private void openPiano() {
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(pianoFrame.getPanel());
        pianoFrame.setKeyListener(this);
        mainMenu.revalidate();
        mainMenu.repaint();
        pianoFrame.getPanel().requestFocusInWindow();
    }

    private void openXylophone() {
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(xylophoneFrame.getPanel());
        mainMenu.revalidate();
        mainMenu.repaint();
    }

    private void openVideoGames() {
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(videoGamesFrame.getPanel());
        mainMenu.revalidate();
        mainMenu.repaint();
    }

    private void openOrgan() {
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(organFrame.getPanel());
        mainMenu.revalidate();
        mainMenu.repaint();
    }

    private void openDrums() {
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(pianoFrame.getPanel());
        mainMenu.revalidate();
        mainMenu.repaint();
    }

    private void openMainMenu() {
        // Vider le contentPane
        mainMenu.getContentPane().removeAll();

        // Reconfigurer l'interface
        mainMenu.initializeUI();

        // RafraÃ®chir l'affichage
        mainMenu.revalidate();
        mainMenu.repaint();
    }


    @Override
    public void setMainMenu(final IMainMenu mainMenu) {
        this.mainMenu = mainMenu;
        this.mainMenu.setInstrumentSelectedListener(this);
        this.mainMenu.setVisible(true);
    }

    @Override
    public void setPianoFrame(final IPianoFrame pianoFrame) {
        this.pianoFrame = pianoFrame;
        this.pianoFrame.setController(this);
    }

    @Override
    public void start() {
        // Lancement de l'application si besoin
    }

    @Override
    public void stop() {
        // Fermeture ou nettoyage
    }

    @Override
    public IView getView() {
        return null;
        // À implémenter si nécessaire
    }

    @Override
    public void setView(final IView view) {
        // À implémenter si nécessaire
    }

    @Override
    public IModel getModel() {
        return null;
        // À implémenter si nécessaire
    }

    @Override
    public void setModel(final IModel model) {
        // À implémenter si nécessaire
    }

    @Override
    public void onKeyPressed(int key, int octave) {
        int midiNote = musicPlayer.getMidiNote(octave, key);
        musicPlayer.playNote(midiNote);
    }

    @Override
    public void onKeyReleased(int key, int octave) {
        int midiNote = musicPlayer.getMidiNote(octave, key);
        musicPlayer.stopNote(midiNote);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = convertKeyToNote(e.getKeyCode());
        if (key >= 0) {
            onKeyPressed(key, KEYBOARD_OCTAVE);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = convertKeyToNote(e.getKeyCode());
        if (key >= 0) {
            onKeyReleased(key, KEYBOARD_OCTAVE);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Non utilisé
    }

    private int convertKeyToNote(int keyCode) {
        return switch (keyCode) {
            case KeyEvent.VK_A -> 0;
            case KeyEvent.VK_Z -> 1;
            case KeyEvent.VK_E -> 2;
            case KeyEvent.VK_R -> 3;
            case KeyEvent.VK_T -> 4;
            case KeyEvent.VK_Y -> 5;
            case KeyEvent.VK_U -> 6;
            default -> -1;
        };

    }

    public void setPianoController(IPianoController pianoController) {
        this.pianoController = pianoController;
    }
}
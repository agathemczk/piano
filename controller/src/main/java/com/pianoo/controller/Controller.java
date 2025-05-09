package com.pianoo.controller;

import com.pianoo.model.IModel;
import com.pianoo.view.IOnInstrumentSelectedListener;
import com.pianoo.view.IPianoFrame;
import com.pianoo.view.IMainMenu;
import com.pianoo.view.IView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements IController, IOnInstrumentSelectedListener, KeyListener {

    private final IMusicPlayer musicPlayer;;
    private IPianoFrame pianoFrame;
    private IPianoController pianoController;
    private IMainMenu mainMenu;

    private static final int KEYBOARD_OCTAVE = 4;

    public Controller(IMusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }


    @Override
    public void onInstrumentSelected(String instrumentName) {
        if ("Piano".equalsIgnoreCase(instrumentName)) {
            openPiano();
        }
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

    private void openPiano() {
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(pianoFrame.getPanel());
        pianoFrame.setKeyListener(this);
        mainMenu.revalidate();
        mainMenu.repaint();
        pianoFrame.getPanel().requestFocusInWindow();
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
package com.pianoo.controller;

import com.pianoo.model.IModel;
import com.pianoo.view.IOnInstrumentSelectedListener;
import com.pianoo.view.IPianoFrame;
import com.pianoo.view.IMainMenu;
import com.pianoo.view.IView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements IController, IOnInstrumentSelectedListener, KeyListener {

    private final IMusicPlayer musicPlayer;
    private final IMainMenu mainMenu;
    private final IPianoFrame pianoFrame;

    public Controller(IMusicPlayer musicPlayer, IMainMenu mainMenu, IPianoFrame pianoFrame) {
        this.musicPlayer = musicPlayer;
        this.mainMenu = mainMenu;
        this.pianoFrame = pianoFrame;
        this.mainMenu.setInstrumentSelectedListener(this);
        this.mainMenu.setVisible(true);
    }

    @Override
    public void onInstrumentSelected(String instrumentName) {
        if ("Piano".equalsIgnoreCase(instrumentName)) {
            openPiano();
        }
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

    }

    @Override
    public void stop() {

    }

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(final IView view) {

    }

    @Override
    public IModel getModel() {
        return null;
    }

    @Override
    public void setModel(final IModel model) {

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
            onKeyPressed(key, 3); // Octave en dur (à dynamiser si nécessaire)
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = convertKeyToNote(e.getKeyCode());
        if (key >= 0) {
            onKeyReleased(key, 3);
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
}

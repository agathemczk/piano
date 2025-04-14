package com.pianoo.controller;

import com.pianoo.model.IModel;
import com.pianoo.view.IMainMenu;
import com.pianoo.view.IView;
import com.pianoo.view.IOnInstrumentSelectedListener;
import com.pianoo.view.IPianoFrame;

public class Controller implements IController, IOnInstrumentSelectedListener {
    private IMusicPlayer musicPlayer;
    private IMainMenu mainMenu;
    private IPianoFrame pianoFrame;

    public Controller(IMusicPlayer musicPlayer, IMainMenu mainMenu, IPianoFrame pianoFrame) {
        this.musicPlayer = musicPlayer;
        this.mainMenu = mainMenu;
        this.pianoFrame = pianoFrame;
        this.mainMenu.setInstrumentSelectedListener(this); // 👈 liaison
        this.mainMenu.setVisible(true);
    }

    @Override
    public void onInstrumentSelected(String instrumentName) {
        if ("Piano".equals(instrumentName)) {
            openPiano();
        }
    }

    private void openPiano() {
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(pianoFrame.getPanel()); // Utilise l'interface IPianoFrame
        mainMenu.revalidate();
        mainMenu.repaint();
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
}
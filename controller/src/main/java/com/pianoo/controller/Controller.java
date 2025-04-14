package com.pianoo.controller;

import com.pianoo.model.IModel;
import com.pianoo.view.*;
import com.pianoo.model.IVideoGames;

//import com.pianoo.model.IDrums;


public class Controller implements IController, IOnInstrumentSelectedListener {
    private IMusicPlayer musicPlayer;
    private IMainMenu mainMenu;
    private IPianoFrame pianoFrame;
    private IOrganFrame organFrame;
    private IXylophoneFrame xylophoneFrame;
    private IVideoGamesFrame videoGamesFrame;

    public Controller(IMusicPlayer musicPlayer, IMainMenu mainMenu, IPianoFrame pianoFrame, IOrganFrame organFrame, IXylophoneFrame xylophoneFrame, IVideoGamesFrame videoGamesFrame) {
        this.musicPlayer = musicPlayer;
        this.mainMenu = mainMenu;
        this.pianoFrame = pianoFrame;
        this.organFrame = organFrame;
        this.xylophoneFrame = xylophoneFrame;
        this.videoGamesFrame = videoGamesFrame;
        this.mainMenu.setInstrumentSelectedListener(this);
        this.mainMenu.setVisible(true);
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
        if ("Quit".equals(instrumentName)) {
            System.exit(0);
        }
    }

    private void openPiano() {
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(pianoFrame.getPanel());
        mainMenu.revalidate();
        mainMenu.repaint();
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
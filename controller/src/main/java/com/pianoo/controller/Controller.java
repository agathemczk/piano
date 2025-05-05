package com.pianoo.controller;

import com.pianoo.model.IModel;
import com.pianoo.view.*;

//import com.pianoo.model.IDrums;


public class Controller implements IController, IOnChoiceSelectedListener, IMenuNavigationListener {
    private IMusicPlayer musicPlayer;
    private IMainMenu mainMenu;
    private IPianoFrame pianoFrame;
    private IOrganFrame organFrame;
    private IXylophoneFrame xylophoneFrame;
    private IVideoGamesFrame videoGamesFrame;
    private IRoundCloseButton roundCloseButton;


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
            System.out.println("Organ");
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
        mainMenu.removeAll();
        mainMenu.add(pianoFrame.getPanel());
        mainMenu.revalidate();
        mainMenu.repaint();
    }

    private void openXylophone() {
        mainMenu.removeAll();
        mainMenu.add(xylophoneFrame.getPanel());
        mainMenu.revalidate();
        mainMenu.repaint();
    }

    private void openVideoGames() {
        mainMenu.removeAll();
        mainMenu.add(videoGamesFrame.getPanel());
        mainMenu.revalidate();
        mainMenu.repaint();
    }

    private void openOrgan() {
        mainMenu.removeAll();
        mainMenu.add(organFrame.getPanel());
        mainMenu.revalidate();
        mainMenu.repaint();
    }

    private void openDrums() {
        mainMenu.removeAll();
        mainMenu.add(pianoFrame.getPanel());
        mainMenu.revalidate();
        mainMenu.repaint();
    }

    private void openMainMenu() {
        mainMenu.removeAll(); // Supprime tous les composants existants
        mainMenu.add(mainMenu.getPanel()); // Ajoute le menu principal
        mainMenu.revalidate(); // Revalide le conteneur pour appliquer les changements
        mainMenu.repaint(); // Redessine le conteneur
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
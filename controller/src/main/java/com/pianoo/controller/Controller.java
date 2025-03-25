package com.pianoo.controller;

import com.pianoo.model.IModel;
import com.pianoo.view.IMainMenu;
import com.pianoo.view.IView;

public class Controller implements IController{
    private IMusicPlayer musicPlayer;
    private IMainMenu mainMenu;

    public Controller(IMusicPlayer musicPlayer, IMainMenu mainMenu) {
        this.musicPlayer = musicPlayer;
        this.mainMenu = mainMenu;
        this.mainMenu.setVisible(true);
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

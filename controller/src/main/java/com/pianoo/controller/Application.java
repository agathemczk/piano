package com.pianoo.controller;

import com.pianoo.model.IInstrument;
import com.pianoo.model.IMusicPlayer;
//import com.pianoo.model.ScoreParser;
import com.pianoo.model.IRecord;
import com.pianoo.view.IMainMenu;

public class Application implements IApplication {

    private IInstrument activeInstrument;
    private IMainMenu mainMenu;
    private IMusicPlayer player;
    private IRecord record;
    private boolean isRecording;

    @Override
    public void start() {

    }

    @Override
    public void chosseInstrument(final IInstrument instrument) {

    }

    @Override
    public void goingToRecord(final boolean isRecording) {

    }

    @Override
    public void saveRecord(final String fileName) {

    }

    /*@Override
    public void loadAndPlayParition(final ScoreParser partition) {

    }*/

    @Override
    public void quit() {

    }
}

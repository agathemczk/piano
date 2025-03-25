package com.pianoo.controller;

import com.pianoo.model.IInstrument;
import com.pianoo.model.IPartition;
import com.pianoo.model.IRecord;
import com.pianoo.view.IMainMenu;

import javax.sound.midi.Instrument;

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

    @Override
    public void loadAndPlayParition(final IPartition partition) {

    }

    @Override
    public void quit() {

    }
}

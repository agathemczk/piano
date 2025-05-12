package com.pianoo.controller;

import com.pianoo.model.IInstrument;
import com.pianoo.model.IMusicPlayer;
import com.pianoo.model.IPartition;
import com.pianoo.model.IRecord;
import com.pianoo.view.IMainMenu;

public interface IApplication {
    IInstrument activeInstrument = null;
    IMainMenu mainMenu = null;
    IMusicPlayer player = null;
    IRecord record = null;
    boolean isRecording = false;

    void start();
    void chosseInstrument(IInstrument instrument);
    void goingToRecord(boolean isRecording);
    void saveRecord(String fileName);
    void loadAndPlayParition(IPartition partition);
    void quit();

}

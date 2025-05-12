package com.pianoo.controller;
import com.pianoo.model.IKeyboardMapping;
import com.pianoo.view.IMainMenu;
import com.pianoo.view.IPianoFrame;

public interface IController {

    void onPlayCat();

    void start();

    void stop();

    void onKeyPressed(int key, int octave);

    void onKeyReleased(int key, int octave);

    void onInstrumentSelected(String instrumentName);

    void setMainMenu(IMainMenu mainMenu);

    void setPianoFrame(IPianoFrame pianoFrame);

    void setKeyboardMapping(IKeyboardMapping keyboardMapping);
}

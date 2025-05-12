package com.pianoo.controller;
import com.pianoo.model.IKeyboardMapping;
import com.pianoo.view.*;


public interface IController {

    void onPlayCat();

    void start();

    void stop();

    IView getView();

    void setView(IView view);

    void onKeyPressed(int key, int octave);

    void onKeyReleased(int key, int octave);

    void onInstrumentSelected(String instrumentName);

    void setMainMenu(IMainMenu mainMenu);

    void setPianoFrame(IPianoFrame pianoFrame);

    void setXylophoneFrame(IXylophoneFrame xylophoneFrame);

    void setKeyboardMapping(IKeyboardMapping keyboardMapping);

    void onNotePlayed(String note);
}

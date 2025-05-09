package com.pianoo.controller;
import com.pianoo.view.IMainMenu;
import com.pianoo.view.IPianoFrame;
import com.pianoo.view.IView;

public interface IController {
    void start();

    void stop();

    IView getView();

    void setView(IView view);

    void onKeyPressed(int key, int octave);

    void onKeyReleased(int key, int octave);

    void onInstrumentSelected(String instrumentName);

    void setMainMenu(IMainMenu mainMenu);

    void setPianoFrame(IPianoFrame pianoFrame);
}

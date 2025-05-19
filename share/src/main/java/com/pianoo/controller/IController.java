package com.pianoo.controller;
import com.pianoo.view.*;


public interface IController {

    void onOrganKeyPressed(int midiNote);

    void onOrganKeyReleased(int midiNote);

    int getMidiNoteFromKeyName(String noteName);

    boolean isNoteActive(int midiNote);

    int adjustMidiNoteForKeyboard(int baseMidiNote, boolean isUpperKeyboard);

    int getMidiNoteForKeyCode(int keyCode);

    void onPlayCat();

    void toggleRecording();

    void onReturnMainMenu();

    void showMainMenu();

    void start();

    void stop();

    void onKeyPressed(int noteValue, int octave);

    void onKeyReleased(int key, int octave);

    void onInstrumentSelected(String instrumentName);

    void onVideoGameNotePressed(String noteName);

    void setPianoFrame(IPianoFrame pianoFrame);

    void setXylophoneFrame(IXylophoneFrame xylophoneFrame);

    void onNotePlayed(String note);

    void onDrumHit(String drumType);
}

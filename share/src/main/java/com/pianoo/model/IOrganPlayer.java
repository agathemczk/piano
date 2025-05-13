package com.pianoo.model;

import java.util.List;

public interface IOrganPlayer extends IMusicPlayer {
    void playNote(int midiNote, int velocity);

    void stopNote(int midiNote);

    boolean isNoteActive(int midiNote);

    int getMidiNoteFromKeyName(String noteName);

    int getMidiNoteForKeyCode(int keyCode);

    int adjustMidiNoteForKeyboard(int baseMidiNote, boolean isUpperKeyboard);
}
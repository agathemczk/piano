package com.pianoo.model;

public interface IOrganPlayer {
    void playNote(int midiNote, int velocity);

    void stopNote(int midiNote);

    boolean isNoteActive(int midiNote);

    int getMidiNoteFromKeyName(String noteName);

    String getNoteNameFromMidi(int midiNote);

    int getMidiNoteForKeyCode(int keyCode);

    int adjustMidiNoteForKeyboard(int baseMidiNote, boolean isUpperKeyboard);
}
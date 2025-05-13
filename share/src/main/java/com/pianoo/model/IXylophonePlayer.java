package com.pianoo.model;

public interface IXylophonePlayer {
    void playNote(int midiNote);
    void playNote(String noteName, String[] availableNotes);
    int getMidiNote(int baseOctave, int key);
}
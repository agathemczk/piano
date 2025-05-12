package com.pianoo.model;

public interface IXylophonePlayer {
    void playNote(int midiNote);
    int getMidiNote(int baseOctave, int key);
}
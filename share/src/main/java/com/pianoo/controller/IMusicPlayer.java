package com.pianoo.controller;

public interface IMusicPlayer {
    void playNote(int midiNote);
    void stopNote(int midiNote);
    int getMidiNote(int baseOctave, int key);
}

package com.pianoo.model;

import java.util.List;

public interface IXylophonePlayer extends IMusicPlayer {
    // playNote(int midiNote) is inherited from IMusicPlayer
    // void playNote(int midiNote);

    void playNote(String noteName, String[] availableNotes);

    // getMidiNote(int baseOctave, int key) is inherited from IMusicPlayer
    // int getMidiNote(int baseOctave, int key);

    // Methods inherited from IMusicPlayer:
    // void stopNote(int midiNote);
    // void setInstrument(String instrument);
    // void playScore(List<IScoreEvent> scoreEvents);
    // void close();
    // void addEffect();
}
package com.pianoo.model;

import java.util.List; // Required for playScore from IMusicPlayer

public interface IDrumsPlayer extends IMusicPlayer { // Extends IMusicPlayer
    void playDrum(String drumType); // Specific to Drums

    // Methods inherited from IMusicPlayer:
    // void playNote(int midiNote);
    // void stopNote(int midiNote);
    // int getMidiNote(int baseOctave, int key);
    // void setInstrument(String instrument);
    // void playScore(List<IScoreEvent> scoreEvents);
    // void close();
    // void addEffect();
}
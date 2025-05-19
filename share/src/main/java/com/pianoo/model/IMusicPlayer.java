package com.pianoo.model;

public interface IMusicPlayer {

    void playNote(int midiNote); //int Instru instru, int msTime

    void stopNote(int midiNote);

    int getMidiNote(int baseOctave, int key);

    String getNoteName(int noteValue, int octave);
}

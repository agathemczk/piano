package com.pianoo.model;

import java.util.List;

public interface IPianoPlayer extends IMusicPlayer {
    void playNote(int midiNote);

    void playNote(String noteName, String[] availableNotes);

    void stopNote(int midiNote);

    void setInstrument(String instrument);

    void playScore(List<IScoreEvent> scoreEvents);

    void close();

    void addEffect();

    int getMidiNote(int baseOctave, int key);
}
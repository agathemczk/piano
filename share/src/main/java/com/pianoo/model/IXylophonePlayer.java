package com.pianoo.model;

import javax.sound.midi.*;

public interface IXylophonePlayer {
    void playNote(int midiNote);

    void stopNote(int midiNote);

    int getMidiNote(int baseOctave, int key);
}

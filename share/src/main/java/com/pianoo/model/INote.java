package com.pianoo.model;

import javax.sound.midi.Instrument;

public interface INote {
    int getNoteValue();
    int getOctave();
    int getMidiNote();
    boolean isBlackKey();
}

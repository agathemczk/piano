package com.pianoo.model;

import javax.sound.midi.Instrument;

public interface INote {
    String name = "";
    int midi = 0;
    double time = 0; //en s

    String getName();
    int getMidi();
    double getTime();
    Instrument getInstrument();
    String toString();
}

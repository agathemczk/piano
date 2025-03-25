package com.pianoo.model;

import javax.sound.midi.Instrument;

public class Note implements INote {

    String name;
    int midi;
    double time;

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getMidi() {
        return 0;
    }

    @Override
    public double getTime() {
        return 0;
    }

    @Override
    public Instrument getInstrument() {
        return null;
    }
}

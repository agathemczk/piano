package com.pianoo.model;

import java.util.Map;

abstract public class Instrument implements IInstrument {
    private String name;
    private Map<String, Sound> sound;


    public Instrument(String name, Map<String, Sound> sound) {
        this.name = name;
        this.sound = sound;
    }

    void displayIntrumentInterface(Instrument instrument) {
    }

}

package com.pianoo.model;

import java.util.Map;

public interface IInstrument {
    String name = "";
    Map<String, ISound> sound;

    public Instrument(String name, Map<String, Sound> sound);
    void displayIntrumentInterface(Instrument instrument);
}

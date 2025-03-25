package com.pianoo.model;

import java.util.Map;

public interface IInstrument {
    String name = "";
    Map<String, ISound> sound = Map.of();

    public void Instrument(String name, Map<String, ISound> sound);
    void displayIntrumentInterface(IInstrument instrument);
}

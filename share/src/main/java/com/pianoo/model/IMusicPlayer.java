package com.pianoo.model;

import javax.sound.midi.*;

public interface IMusicPlayer {
    public Synthesizer synth = null;
    public MidiChannel channel = null;
    //public instrumentMap<String, MidiChannel>();

    void playNote(int midiNote); //int Instru instru, int msTime

    void stopNote(int midiNote);

    int getMidiNote(int baseOctave, int key);

    String getNoteName(int noteValue, int octave);

    void setInstrument(String instrument);

    void close();

    void addEffect();
}

package com.pianoo.model;

import javax.sound.midi.*;
import java.util.List;

public interface IMusicPlayer {
    public Synthesizer synth = null;
    public MidiChannel channel = null;
    //public instrumentMap<String, MidiChannel>();

    void playNote(int midiNote); //int Instru instru, int msTime

    void stopNote(int midiNote);

    int getMidiNote(int baseOctave, int key);

    void setInstrument(String instrument);

    /**
     * Plays a sequence of musical events (notes or silences with duration).
     *
     * @param scoreEvents The list of score events to play.
     */
    void playScore(List<IScoreEvent> scoreEvents);

    void close();

    void addEffect();
}

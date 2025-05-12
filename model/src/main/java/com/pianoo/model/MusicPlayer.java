package com.pianoo.model;

import javax.sound.midi.*;

public class MusicPlayer implements IMusicPlayer {
    private Synthesizer synth;
    private MidiChannel channel;
    private static final int VELOCITY = 80;

    public MusicPlayer() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channel = synth.getChannels()[0];
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playNote(int midiNote) {
        channel.noteOn(midiNote, VELOCITY);
    }

    public void stopNote(int midiNote) {
        channel.noteOff(midiNote);
    }

    public int getMidiNote(int baseOctave, int key) {
        return 12 * baseOctave + key;
    }

    @Override
    public void setInstrument(final String instrument) {
    }

    @Override
    public void close() {
    }

    @Override
    public void addEffect() {
    }

}
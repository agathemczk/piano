package com.pianoo.model;

import javax.sound.midi.*;

public class MusicPlayer implements IMusicPlayer {
    private Synthesizer synth;
    private MidiChannel channel;

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
        channel.noteOn(midiNote, 80); // 80 = vélocité (intensité)
    }

    public void stopNote(int midiNote) {
        channel.noteOff(midiNote);
    }

    public int getMidiNote(int baseOctave, int key) {
        return 12 * baseOctave + key;
    }

    @Override
    public void close() {
    }

    @Override
    public void addEffect() {
    }

}
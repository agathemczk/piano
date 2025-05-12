package com.pianoo.model;

import javax.sound.midi.*;

public class XylophonePlayer implements IXylophonePlayer {

    private Synthesizer synth;
    private MidiChannel channel;
    private static final int XYLOPHONE_WITH_MIDI = 13;
    private static final int VELOCITY = 80;

    public XylophonePlayer() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();

            Soundbank sb = synth.getDefaultSoundbank();
            if (sb != null) {
                synth.loadAllInstruments(sb);
            }

            channel = synth.getChannels()[0];
            channel.programChange(XYLOPHONE_WITH_MIDI);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playNote(int midiNote) {
        if (channel != null) {
            channel.noteOn(midiNote, VELOCITY);
            System.out.println("Playing note: " + midiNote);
        }
    }

    @Override
    public void stopNote(int midiNote) {
        if (channel != null) {
            channel.noteOff(midiNote);
            System.out.println("Stopping note: " + midiNote);
        }
    }

    @Override
    public int getMidiNote(int baseOctave, int key) {
        return 12 * baseOctave + key;
    }

}

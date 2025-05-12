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

    public void playNote(String noteName, String[] availableNotes) {
        int noteIndex = convertNoteNameToIndex(noteName, availableNotes);
        if (noteIndex >= 0) {
            // Utiliser l'octave 5 pour un son plus aigu
            int midiNote = getMidiNote(5, noteIndex);
            playNote(midiNote);
        }
    }

    private int convertNoteNameToIndex(String noteName, String[] availableNotes) {
        for (int i = 0; i < availableNotes.length; i++) {
            if (availableNotes[i].equals(noteName)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getMidiNote(int baseOctave, int key) {
        return 12 * baseOctave + key;
    }
}
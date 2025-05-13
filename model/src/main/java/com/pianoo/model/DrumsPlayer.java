package com.pianoo.model;

import javax.sound.midi.*;
import java.util.HashMap;
import java.util.Map;

public class DrumsPlayer implements IDrumsPlayer {

    private Synthesizer synth;
    private MidiChannel channel;
    private static final int DRUMS_CHANNEL = 9; // Canal 10 (indexé à partir de 0) réservé aux percussions
    private static final int VELOCITY = 100;

    // Mapping des types de batterie aux notes MIDI standard
    private final Map<String, Integer> drumMidiNotes;

    public DrumsPlayer() {
        drumMidiNotes = new HashMap<>();
        initializeDrumMidiNotes();

        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();

            Soundbank sb = synth.getDefaultSoundbank();
            if (sb != null) {
                synth.loadAllInstruments(sb);
            }

            // Pour les percussions, on utilise toujours le canal 10 (index 9)
            channel = synth.getChannels()[DRUMS_CHANNEL];
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void initializeDrumMidiNotes() {
        // Les numéros correspondent aux notes MIDI standard pour les percussions
        drumMidiNotes.put("Grosse Caisse", 36);      // Bass Drum 1
        drumMidiNotes.put("Caisse Claire", 38);     // Acoustic Snare
        drumMidiNotes.put("Hi-Hat", 42);    // Closed Hi-Hat
        drumMidiNotes.put("Tom Alto", 48);      // Hi-Mid Tom
        drumMidiNotes.put("Tom Medium", 45);      // Low Tom
        drumMidiNotes.put("Tom Basse", 41);  // Low Floor Tom
        drumMidiNotes.put("Ride", 51);  // Ride Cymbal 1
        drumMidiNotes.put("Crash", 49);     // Crash Cymbal 1
    }

    @Override
    public void playDrum(String drumType) {
        if (channel != null && drumMidiNotes.containsKey(drumType)) {
            int midiNote = drumMidiNotes.get(drumType);
            channel.noteOn(midiNote, VELOCITY);
            System.out.println("Jouant percussion: " + drumType + " (note MIDI: " + midiNote + ")");
        } else {
            System.err.println("Type de percussion non reconnu ou canal MIDI indisponible: " + drumType);
        }
    }
}
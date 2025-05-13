package com.pianoo.model;

import javax.sound.midi.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrumsPlayer implements IDrumsPlayer {

    private Synthesizer synth;
    private MidiChannel channel;
    private static final int DRUMS_CHANNEL_INDEX = 9; // Canal 10 (indexé à partir de 0) réservé aux percussions
    private static final int DEFAULT_VELOCITY = 100;
    private static final int SILENCE_MIDI_NOTE = -1; // Consistent with ScoreReader

    // Mapping des types de batterie aux notes MIDI standard
    private final Map<String, Integer> drumMidiNotes;

    public DrumsPlayer() {
        drumMidiNotes = new HashMap<>();
        initializeDrumMidiNotes();

        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();

            // Soundbank sb = synth.getDefaultSoundbank();
            // if (sb != null) {
            //    synth.loadAllInstruments(sb);
            // }

            if (synth.getChannels() != null && synth.getChannels().length > DRUMS_CHANNEL_INDEX) {
                channel = synth.getChannels()[DRUMS_CHANNEL_INDEX];
                // For drums, programChange is often not needed or selects a drum kit.
                // Default is usually GM Standard Drum Kit on channel 10 (index 9).
                // channel.programChange(0); // Example: Sets to GM Standard Drum Kit if needed
            } else {
                System.err.println("Drum channel (10) not available or synthesizer has too few channels!");
            }
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void initializeDrumMidiNotes() {
        // Les numéros correspondent aux notes MIDI standard pour les percussions
        drumMidiNotes.put("Grosse Caisse", 36);      // Bass Drum 1
        drumMidiNotes.put("Caisse Claire", 38);     // Acoustic Snare
        drumMidiNotes.put("Hi-Hat Ferme", 42);    // Closed Hi-Hat (changed from Hi-Hat for clarity)
        drumMidiNotes.put("Hi-Hat Ouvert", 46);   // Open Hi-Hat
        drumMidiNotes.put("Tom Alto", 48);      // Hi-Mid Tom
        drumMidiNotes.put("Tom Medium", 45);      // Low Tom
        drumMidiNotes.put("Tom Basse", 41);  // Low Floor Tom
        drumMidiNotes.put("Ride Cymbale", 51);  // Ride Cymbal 1 (changed for clarity)
        drumMidiNotes.put("Crash Cymbale", 49);     // Crash Cymbal 1 (changed for clarity)
        // Add more drum sounds if needed by partitions
        drumMidiNotes.put("Kick", 36); // Alias for Bass Drum
        drumMidiNotes.put("Snare", 38); // Alias
        drumMidiNotes.put("ClosedHH", 42); // Alias
        drumMidiNotes.put("OpenHH", 46); // Alias
        drumMidiNotes.put("Crash", 49); // Alias
        drumMidiNotes.put("Ride", 51); // Alias
    }

    @Override
    public void playDrum(String drumType) {
        if (channel != null && drumMidiNotes.containsKey(drumType)) {
            int midiNote = drumMidiNotes.get(drumType);
            channel.noteOn(midiNote, DEFAULT_VELOCITY);
            // System.out.println("Jouant percussion: " + drumType + " (note MIDI: " + midiNote + ")");
        } else {
            System.err.println("Type de percussion non reconnu ou canal MIDI indisponible: " + drumType);
        }
    }

    // --- IMusicPlayer Implementation ---
    @Override
    public void playNote(int midiNote) {
        if (channel != null) {
            // In the context of playScore for drums, midiNote is directly a drum sound.
            channel.noteOn(midiNote, DEFAULT_VELOCITY);
        }
    }

    @Override
    public void stopNote(int midiNote) {
        // For most drum sounds, noteOff does little or nothing as they are percussive (short decay).
        // However, some sounds like cymbals or open hi-hats might be affected by noteOff or allNotesOff.
        // For simplicity in playScore, we might not call stopNote for each drum hit, 
        // relying on their natural decay, or call it if the score indicates a very short hit specifically.
        // Or, it could be used to implement choking a cymbal.
        if (channel != null) {
            channel.noteOff(midiNote, 0); // Velocity 0 for noteOff
        }
    }

    @Override
    public int getMidiNote(int baseOctave, int key) {
        // This method is less relevant for a drum player in the same way as melodic instruments.
        // It could return a drum sound based on a key, but ScoreReader will provide direct MIDI notes.
        // Returning -1 or a default drum sound if a generic mapping is ever attempted.
        System.err.println("getMidiNote(baseOctave, key) is not typically used for DrumsPlayer like this. Score should provide direct MIDI drum notes.");
        return -1;
    }

    @Override
    public void setInstrument(String instrument) {
        // DrumsPlayer uses a dedicated drum channel (10, index 9).
        // The "instrument" is the drum kit. Changing it involves a program change on this channel.
        // For GM MIDI, program 0 on channel 10 is usually "Standard Kit".
        System.out.println("DrumsPlayer: setInstrument called with '" + instrument + "'. The drum channel is fixed (10).");
        System.out.println("To change drum kits, a specific program change on channel 10 would be needed (e.g., based on kit name).");
        // Example: if (instrument.equalsIgnoreCase("Jazz Kit")) channel.programChange(JAZZ_KIT_PROGRAM_NUMBER);
        // By default, we assume the synth provides a standard kit on channel 10.
    }

    @Override
    public void playScore(List<IScoreEvent> scoreEvents) {
        if (channel == null) {
            System.err.println("Cannot play score on Drums, MIDI channel is not available.");
            return;
        }
        if (scoreEvents == null || scoreEvents.isEmpty()) {
            System.out.println("No score events to play on Drums.");
            return;
        }

        System.out.println("DrumsPlayer starting to play score...");
        new Thread(() -> {
            for (IScoreEvent event : scoreEvents) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("DrumsPlayer playback thread interrupted, stopping score.");
                    break;
                }

                int midiNote = event.getMidiNote();
                long durationMs = (long) (event.getDurationSeconds() * 1000);

                if (midiNote != SILENCE_MIDI_NOTE) {
                    playNote(midiNote); // This will call DrumsPlayer.playNote(int)
                    // which does channel.noteOn for the drum sound.
                    // For drums, the sound usually decays naturally. 
                    // The duration from the score primarily dictates when the *next* event occurs.
                    // We don't typically hold a drum note then stop it like a piano note.
                    // So, we just sleep for the event's duration to time the next event.
                    if (durationMs > 0) { // Only sleep if duration is positive
                        try {
                            Thread.sleep(durationMs);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.err.println("DrumsPlayer playback interrupted during note/timing pause.");
                            // Unlike melodic instruments, a specific stopNote(midiNote) might not be critical here,
                            // as the sound is percussive. But if a long sound was cut short, it might matter.
                            break;
                        }
                    }
                    // No explicit stopNote(midiNote) here for typical drum score playback.
                    // If a score *needs* a cymbal choke, it would require special handling or a specific MIDI note off.
                } else {
                    // This is a silence
                    if (durationMs > 0) {
                        try {
                            Thread.sleep(durationMs);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.err.println("DrumsPlayer playback interrupted during silence.");
                            break;
                        }
                    }
                }
            }
            System.out.println("DrumsPlayer finished playing score.");
        }).start();
    }

    @Override
    public void close() {
        if (synth != null && synth.isOpen()) {
            if (channel != null) {
                channel.allNotesOff(); // Good practice for any channel
            }
            synth.close();
            System.out.println("DrumsPlayer synthesizer closed.");
        }
    }

    @Override
    public void addEffect() {
        System.out.println("addEffect not implemented in DrumsPlayer.");
    }
}
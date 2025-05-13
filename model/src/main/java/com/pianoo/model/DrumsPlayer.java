package com.pianoo.model;

import javax.sound.midi.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrumsPlayer implements IDrumsPlayer {

    private Synthesizer synth;
    private MidiChannel channel;
    private volatile Thread scorePlayingThread = null; // Thread for score playback

    private static final int DRUMS_CHANNEL_INDEX = 9; // Canal 10 (indexé à partir de 0) réservé aux percussions
    private static final int DEFAULT_VELOCITY = 100;
    private static final int SILENCE_MIDI_NOTE = -1; // Consistent with ScoreReader

    private final Map<String, Integer> drumMidiNotes;

    public DrumsPlayer() {
        drumMidiNotes = new HashMap<>();
        initializeDrumMidiNotes();

        try {
            synth = MidiSystem.getSynthesizer();
            if (!synth.isOpen()) {
                synth.open();
            }

            if (synth.getChannels() != null && synth.getChannels().length > DRUMS_CHANNEL_INDEX) {
                channel = synth.getChannels()[DRUMS_CHANNEL_INDEX];
                // channel.programChange(0); // Default GM Standard Drum Kit often implicitly on channel 10
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
        drumMidiNotes.put("Kick", 36);
        drumMidiNotes.put("Snare", 38);
        drumMidiNotes.put("ClosedHH", 42);
        drumMidiNotes.put("OpenHH", 46);
        drumMidiNotes.put("Crash", 49);
        drumMidiNotes.put("Ride", 51);
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

    @Override
    public void playNote(int midiNote) {
        if (channel != null) {
            channel.noteOn(midiNote, DEFAULT_VELOCITY);
        }
    }

    @Override
    public void stopNote(int midiNote) {
        if (channel != null) {
            channel.noteOff(midiNote, 0);
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

        if (scorePlayingThread != null && scorePlayingThread.isAlive()) {
            System.out.println("DrumsPlayer: Stopping previous score playback.");
            scorePlayingThread.interrupt();
            try {
                scorePlayingThread.join(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("DrumsPlayer starting to play score...");
        scorePlayingThread = new Thread(() -> {
            try {
                for (IScoreEvent event : scoreEvents) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("DrumsPlayer playback thread interrupted, stopping score.");
                        // For drums, allNotesOff might be too aggressive if only one part of kit was playing.
                        // However, individual noteOffs are less common in drum sequencing this way.
                        // channel.allNotesOff(); // Consider implications.
                        break;
                    }

                    int midiNote = event.getMidiNote();
                    long durationMs = (long) (event.getDurationSeconds() * 1000);

                    if (midiNote != SILENCE_MIDI_NOTE) {
                        playNote(midiNote); // This calls channel.noteOn()
                        // For drums, the duration from the score primarily dictates when the *next* event occurs.
                        // We don't typically hold a drum note then stop it like a piano note.
                        // So, we just sleep for the event's duration to time the next event.
                        if (durationMs > 0) {
                            try {
                                Thread.sleep(durationMs);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                System.err.println("DrumsPlayer playback interrupted during note/timing pause.");
                                // No explicit stopNote(midiNote) here for typical drum score playback.
                                break;
                            }
                        }
                        // Unlike melodic instruments, stopNote(midiNote) is not typically called after each drum hit here.
                        // Its effect is often minimal for percussive sounds.
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
            } finally {
                System.out.println("DrumsPlayer finished playing score or was interrupted.");
            }
        });
        scorePlayingThread.setDaemon(true);
        scorePlayingThread.start();
    }

    @Override
    public void close() {
        System.out.println("DrumsPlayer.close() called. Stopping notes and interrupting score thread.");
        if (channel != null) {
            channel.allNotesOff(); // Stop all drum sounds on this channel
        }
        if (scorePlayingThread != null && scorePlayingThread.isAlive()) {
            scorePlayingThread.interrupt();
            try {
                scorePlayingThread.join(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            scorePlayingThread = null;
        }
        // synth.close(); // Do not close synthesizer here
        System.out.println("DrumsPlayer resources cleaned. Synthesizer remains open.");
    }

    @Override
    public void addEffect() {
        System.out.println("addEffect not implemented in DrumsPlayer.");
    }
}
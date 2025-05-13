package com.pianoo.model;

import javax.sound.midi.*;
import java.util.List;

public class PianoPlayer implements IPianoPlayer {

    private Synthesizer synth;
    private MidiChannel channel;
    private volatile Thread scorePlayingThread = null;
    private static final int PIANO_MIDI_PROGRAM = 1; // Acoustic Grand Piano
    private static final int DEFAULT_VELOCITY = 100;
    private static final int SILENCE_MIDI_NOTE = -1;

    public PianoPlayer() {
        try {
            synth = MidiSystem.getSynthesizer();
            if (!synth.isOpen()) {
                synth.open();
            }

            if (synth.getChannels() != null && synth.getChannels().length > 0) {
                // Find a free channel or use a specific one for piano
                // For simplicity, using channel 0, but can be made more robust
                channel = synth.getChannels()[0];
                channel.programChange(PIANO_MIDI_PROGRAM);
            } else {
                System.err.println("No MIDI channels available for PianoPlayer!");
            }
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playNote(int midiNote) {
        if (channel != null) {
            channel.noteOn(midiNote, DEFAULT_VELOCITY);
        }
    }

    @Override
    public void playNote(String noteName, String[] availableNotes) {
        int noteIndex = convertNoteNameToIndex(noteName, availableNotes);
        if (noteIndex >= 0) {
            // Assuming a standard 88-key piano, starting from A0 (MIDI note 21)
            // This part might need adjustment based on your `availableNotes` mapping
            // For a simple approach, let's use a base octave and calculate from there.
            int midiNote = getMidiNote(4, noteIndex); // Example: Middle C octave for note index 0
            playNote(midiNote);
        }
    }

    private int convertNoteNameToIndex(String noteName, String[] availableNotes) {
        for (int i = 0; i < availableNotes.length; i++) {
            if (availableNotes[i].equalsIgnoreCase(noteName)) {
                return i;
            }
        }
        return -1; // Note not found
    }

    @Override
    public int getMidiNote(int baseOctave, int key) {
        // Standard MIDI note calculation: C4 (Middle C) is MIDI note 60.
        // A0 is 21, C8 is 108 for an 88-key piano.
        // This function might need to be more sophisticated depending on `key`'s meaning.
        // If `key` is an offset from a base note (e.g., C in the octave),
        // then this calculation is: (baseOctave * 12) + key_offset_from_C + C_MIDI_BASE_NOTE
        // For simplicity, following Xylophone's model directly:
        return 12 * baseOctave + key; // This implies 'key' is an index from the start of an octave or similar.
    }

    @Override
    public void stopNote(int midiNote) {
        if (channel != null) {
            channel.noteOff(midiNote);
        }
    }

    @Override
    public void setInstrument(String instrument) {
        // The PianoPlayer is for Piano, but we could allow different piano types if needed.
        // For now, it's fixed to Acoustic Grand Piano.
        System.out.println("PianoPlayer instrument is fixed to Acoustic Grand Piano (Program " + PIANO_MIDI_PROGRAM + "). Call to setInstrument ignored.");
        if (channel != null && channel.getProgram() != PIANO_MIDI_PROGRAM) {
            channel.programChange(PIANO_MIDI_PROGRAM);
        }
    }

    @Override
    public void playScore(List<IScoreEvent> scoreEvents) {
        if (channel == null) {
            System.err.println("Cannot play score on Piano, MIDI channel is not available.");
            return;
        }
        if (scoreEvents == null || scoreEvents.isEmpty()) {
            System.out.println("No score events to play on Piano.");
            return;
        }

        if (scorePlayingThread != null && scorePlayingThread.isAlive()) {
            System.out.println("PianoPlayer: Stopping previous score playback.");
            scorePlayingThread.interrupt();
            try {
                scorePlayingThread.join(500); // Wait for the thread to die
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("PianoPlayer starting to play score...");
        scorePlayingThread = new Thread(() -> {
            try {
                for (IScoreEvent event : scoreEvents) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("PianoPlayer playback thread interrupted, stopping score.");
                        channel.allNotesOff(); // Stop all notes if interrupted
                        break;
                    }

                    int midiNote = event.getMidiNote();
                    long durationMs = (long) (event.getDurationSeconds() * 1000);

                    if (midiNote != SILENCE_MIDI_NOTE) {
                        playNote(midiNote);
                        try {
                            Thread.sleep(durationMs);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.err.println("PianoPlayer playback interrupted during note hold.");
                            stopNote(midiNote); // Ensure note is stopped if sleep is interrupted
                            break;
                        }
                        stopNote(midiNote);
                    } else { // Silence
                        if (durationMs > 0) {
                            try {
                                Thread.sleep(durationMs);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                                System.err.println("PianoPlayer playback interrupted during silence.");
                                break;
                            }
                        }
                    }
                }
            } finally {
                System.out.println("PianoPlayer finished playing score or was interrupted.");
            }
        });
        scorePlayingThread.setDaemon(true);
        scorePlayingThread.start();
    }

    @Override
    public void close() {
        System.out.println("PianoPlayer.close() called. Stopping notes and interrupting score thread.");
        if (channel != null) {
            channel.allNotesOff();
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
        // We don't close the synthesizer here, as it might be shared.
        // System.out.println("PianoPlayer resources cleaned. Synthesizer remains open.");
    }

    @Override
    public void addEffect() {
        // Placeholder for piano-specific effects (e.g., pedal)
        System.out.println("addEffect not implemented in PianoPlayer yet.");
    }
}


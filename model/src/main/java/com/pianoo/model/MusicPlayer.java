package com.pianoo.model;

import javax.sound.midi.*;
import java.util.List;

public class MusicPlayer implements IMusicPlayer {
    private Synthesizer synth;
    private MidiChannel channel;
    private static final int VELOCITY = 80;
    private static final int SILENCE_MIDI_NOTE = -1; // Matches ScoreReader

    public MusicPlayer() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            // It's good practice to check if synth.getChannels() is empty
            if (synth.getChannels() != null && synth.getChannels().length > 0) {
                channel = synth.getChannels()[0];
            } else {
                System.err.println("No MIDI channels available!");
                // Handle error appropriately, maybe throw an exception or set channel to null and check later
            }
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
            // Consider re-throwing as a custom exception or logging more formally
        }
    }

    @Override
    public void playNote(int midiNote) {
        if (channel != null) {
            channel.noteOn(midiNote, VELOCITY);
        }
    }

    @Override
    public void stopNote(int midiNote) {
        if (channel != null) {
            channel.noteOff(midiNote);
        }
    }

    @Override
    public int getMidiNote(int baseOctave, int key) {
        return 12 * baseOctave + key;
    }

    @Override
    public void setInstrument(final String instrumentName) {
        // Implementation for setInstrument will be needed here
        // This typically involves loading an instrument soundbank (if not default)
        // and then using channel.programChange(instrumentId)
        // For now, let's log that it's called.
        System.out.println("Attempting to set instrument to: " + instrumentName);

        if (synth == null || channel == null) {
            System.err.println("Cannot set instrument, synthesizer or channel not available.");
            return;
        }

        Soundbank soundbank = synth.getDefaultSoundbank();
        if (soundbank != null) {
            Instrument[] instruments = soundbank.getInstruments();
            Instrument selectedInstrument = null;
            for (Instrument inst : instruments) {
                if (inst.getName().trim().equalsIgnoreCase(instrumentName.trim())) {
                    selectedInstrument = inst;
                    break;
                }
            }

            if (selectedInstrument != null) {
                Patch patch = selectedInstrument.getPatch();
                channel.programChange(patch.getBank(), patch.getProgram());
                System.out.println("Instrument changed to: " + selectedInstrument.getName());
            } else {
                System.err.println("Instrument not found: " + instrumentName);
                System.out.println("Available instruments:");
                for (Instrument inst : instruments) {
                    System.out.println("- " + inst.getName());
                }
            }
        } else {
            System.err.println("Default soundbank not available. Cannot change instrument.");
        }
    }

    @Override
    public void playScore(List<IScoreEvent> scoreEvents) {
        if (channel == null) {
            System.err.println("Cannot play score, MIDI channel is not available.");
            return;
        }
        if (scoreEvents == null || scoreEvents.isEmpty()) {
            System.out.println("No score events to play.");
            return;
        }

        System.out.println("Starting to play score...");
        // Run playback in a new thread to avoid blocking the main application thread
        new Thread(() -> {
            for (IScoreEvent event : scoreEvents) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("Playback thread interrupted, stopping score.");
                    break;
                }

                int midiNote = event.getMidiNote();
                // Duration is in seconds, Thread.sleep takes milliseconds
                long durationMs = (long) (event.getDurationSeconds() * 1000);

                if (midiNote != SILENCE_MIDI_NOTE) {
                    playNote(midiNote);
                    try {
                        Thread.sleep(durationMs);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Restore interruption status
                        System.err.println("Playback interrupted during note.");
                        stopNote(midiNote); // Ensure note is stopped if playback is cut short
                        break;
                    }
                    stopNote(midiNote);
                } else {
                    // This is a silence
                    if (durationMs > 0) {
                        try {
                            Thread.sleep(durationMs);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.err.println("Playback interrupted during silence.");
                            break;
                        }
                    }
                }
            }
            System.out.println("Finished playing score.");
        }).start();
    }

    @Override
    public void close() {
        if (synth != null && synth.isOpen()) {
            synth.close();
            System.out.println("Synthesizer closed.");
        }
    }

    @Override
    public void addEffect() {
        // Placeholder for adding effects
        System.out.println("addEffect called - not yet implemented.");
    }
}
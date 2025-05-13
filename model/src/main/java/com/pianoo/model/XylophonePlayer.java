package com.pianoo.model;

import javax.sound.midi.*;
import java.util.List;

public class XylophonePlayer implements IXylophonePlayer {

    private Synthesizer synth;
    private MidiChannel channel;
    private static final int XYLOPHONE_MIDI_PROGRAM = 13;
    private static final int DEFAULT_VELOCITY = 100;
    private static final int SILENCE_MIDI_NOTE = -1;

    public XylophonePlayer() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();

            Soundbank sb = synth.getDefaultSoundbank();
            if (sb != null) {
                synth.loadAllInstruments(sb);
            }

            channel = synth.getChannels()[0];
            channel.programChange(XYLOPHONE_MIDI_PROGRAM);
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

    @Override
    public void stopNote(int midiNote) {
        if (channel != null) {
            channel.noteOff(midiNote);
        }
    }

    @Override
    public void setInstrument(String instrument) {
        System.out.println("XylophonePlayer instrument is fixed to Xylophone (Program " + XYLOPHONE_MIDI_PROGRAM + "). Call to setInstrument ignored.");
        if (channel != null && channel.getProgram() != XYLOPHONE_MIDI_PROGRAM) {
            channel.programChange(XYLOPHONE_MIDI_PROGRAM);
        }
    }

    @Override
    public void playScore(List<IScoreEvent> scoreEvents) {
        if (channel == null) {
            System.err.println("Cannot play score on Xylophone, MIDI channel is not available.");
            return;
        }
        if (scoreEvents == null || scoreEvents.isEmpty()) {
            System.out.println("No score events to play on Xylophone.");
            return;
        }

        System.out.println("XylophonePlayer starting to play score...");
        new Thread(() -> {
            for (IScoreEvent event : scoreEvents) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("XylophonePlayer playback thread interrupted, stopping score.");
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
                        System.err.println("XylophonePlayer playback interrupted during note.");
                        stopNote(midiNote);
                        break;
                    }
                    stopNote(midiNote);
                } else {
                    if (durationMs > 0) {
                        try {
                            Thread.sleep(durationMs);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.err.println("XylophonePlayer playback interrupted during silence.");
                            break;
                        }
                    }
                }
            }
            System.out.println("XylophonePlayer finished playing score.");
        }).start();
    }

    @Override
    public void close() {
        if (synth != null && synth.isOpen()) {
            if (channel != null) {
                channel.allNotesOff();
            }
            synth.close();
            System.out.println("XylophonePlayer synthesizer closed.");
        }
    }

    @Override
    public void addEffect() {
        System.out.println("addEffect not implemented in XylophonePlayer.");
    }
}
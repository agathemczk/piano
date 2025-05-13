package com.pianoo.model;

import javax.sound.midi.*;
import java.util.List;

public class XylophonePlayer implements IXylophonePlayer {

    private Synthesizer synth;
    private MidiChannel channel;
    private volatile Thread scorePlayingThread = null;
    private static final int XYLOPHONE_MIDI_PROGRAM = 13;
    private static final int DEFAULT_VELOCITY = 100;
    private static final int SILENCE_MIDI_NOTE = -1;

    public XylophonePlayer() {
        try {
            synth = MidiSystem.getSynthesizer();
            if (!synth.isOpen()) {
                synth.open();
            }

            if (synth.getChannels() != null && synth.getChannels().length > 0) {
                channel = synth.getChannels()[0];
                channel.programChange(XYLOPHONE_MIDI_PROGRAM);
            } else {
                System.err.println("No MIDI channels available for XylophonePlayer!");
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

        if (scorePlayingThread != null && scorePlayingThread.isAlive()) {
            System.out.println("XylophonePlayer: Stopping previous score playback.");
            scorePlayingThread.interrupt();
            try {
                scorePlayingThread.join(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("XylophonePlayer starting to play score...");
        scorePlayingThread = new Thread(() -> {
            try {
                for (IScoreEvent event : scoreEvents) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("XylophonePlayer playback thread interrupted, stopping score.");
                        channel.allNotesOff();
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
                            System.err.println("XylophonePlayer playback interrupted during note hold.");
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
            } finally {
                System.out.println("XylophonePlayer finished playing score or was interrupted.");
            }
        });
        scorePlayingThread.setDaemon(true);
        scorePlayingThread.start();
    }

    @Override
    public void close() {
        System.out.println("XylophonePlayer.close() called. Stopping notes and interrupting score thread.");
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
        System.out.println("XylophonePlayer resources cleaned. Synthesizer remains open.");
    }

    @Override
    public void addEffect() {
        System.out.println("addEffect not implemented in XylophonePlayer.");
    }
}
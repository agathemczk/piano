package com.pianoo.model;

import javax.sound.midi.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrganPlayer implements IOrganPlayer {
    private final Set<Integer> activeNotes = new HashSet<>();
    private Synthesizer synth;
    private MidiChannel channel;
    private volatile Thread scorePlayingThread = null; // Thread for score playback

    private static final int CHURCH_ORGAN_INSTRUMENT = 19;
    private static final int DEFAULT_VELOCITY = 100;
    private static final int SILENCE_MIDI_NOTE = -1; // Consistent with ScoreReader

    public OrganPlayer() {
        try {
            synth = MidiSystem.getSynthesizer();
            // Keep synth open until application exit or explicit global cleanup
            if (!synth.isOpen()) {
                synth.open();
            }
            if (synth.getChannels() != null && synth.getChannels().length > 0) {
                channel = synth.getChannels()[0];
                channel.programChange(CHURCH_ORGAN_INSTRUMENT);
            } else {
                System.err.println("No MIDI channels available for OrganPlayer!");
            }
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playNote(int midiNote, int velocity) {
        if (channel != null) {
            channel.noteOn(midiNote, velocity);
            activeNotes.add(midiNote);
        }
    }

    @Override
    public void playNote(int midiNote) {
        playNote(midiNote, DEFAULT_VELOCITY);
    }

    @Override
    public void stopNote(int midiNote) {
        if (channel != null) {
            channel.noteOff(midiNote);
            activeNotes.remove(midiNote);
        }
    }

    @Override
    public int getMidiNote(final int baseOctave, final int key) {
        return baseOctave * 12 + key;
    }

    @Override
    public void setInstrument(final String instrument) {
        System.out.println("OrganPlayer instrument is fixed. Call to setInstrument with '" + instrument + "' ignored.");
        if (channel != null && channel.getProgram() != CHURCH_ORGAN_INSTRUMENT) {
            channel.programChange(CHURCH_ORGAN_INSTRUMENT);
        }
    }

    @Override
    public boolean isNoteActive(int midiNote) {
        return activeNotes.contains(midiNote);
    }

    @Override
    public int getMidiNoteFromKeyName(String noteName) {
        // Pour les touches noires avec le format "C#", "D#", etc.
        if (noteName.contains("#")) {
            char note = noteName.charAt(0);
            int octave = Character.getNumericValue(noteName.charAt(noteName.length() - 1));

            // Correspondance pour les notes noires
            int baseNote;
            switch (note) {
                case 'C': baseNote = 1; break;  // C#
                case 'D': baseNote = 3; break;  // D#
                case 'F': baseNote = 6; break;  // F#
                case 'G': baseNote = 8; break;  // G#
                case 'A': baseNote = 10; break; // A#
                default: return -1;
            }

            return 12 * (octave + 3) + baseNote;
        }

        // Extraction de la note et de l'octave pour les touches blanches
        char note = noteName.charAt(0);
        int octave = Character.getNumericValue(noteName.charAt(noteName.length() - 1));

        // Correspondance de base pour les touches blanches
        int baseNote;
        switch (note) {
            case 'C': baseNote = 0; break;
            case 'D': baseNote = 2; break;
            case 'E': baseNote = 4; break;
            case 'F': baseNote = 5; break;
            case 'G': baseNote = 7; break;
            case 'A': baseNote = 9; break;
            case 'B': baseNote = 11; break;
            default: return -1;
        }

        return 12 * (octave + 3) + baseNote;
    }

    @Override
    public int getMidiNoteForKeyCode(int keyCode) {
        switch (keyCode) {
            // Touches blanches
            case java.awt.event.KeyEvent.VK_A: return 60; // Do (C)
            case java.awt.event.KeyEvent.VK_S: return 62; // Ré (D)
            case java.awt.event.KeyEvent.VK_D: return 64; // Mi (E)
            case java.awt.event.KeyEvent.VK_F: return 65; // Fa (F)
            case java.awt.event.KeyEvent.VK_G: return 67; // Sol (G)
            case java.awt.event.KeyEvent.VK_H: return 69; // La (A)
            case java.awt.event.KeyEvent.VK_J: return 71; // Si (B)
            case java.awt.event.KeyEvent.VK_K: return 72; // Do (C) octave supérieure

            // Touches noires
            case java.awt.event.KeyEvent.VK_W: return 61; // Do# (C#)
            case java.awt.event.KeyEvent.VK_E: return 63; // Ré# (D#)
            case java.awt.event.KeyEvent.VK_T: return 66; // Fa# (F#)
            case java.awt.event.KeyEvent.VK_Y: return 68; // Sol# (G#)
            case java.awt.event.KeyEvent.VK_U: return 70; // La# (A#)

            default: return -1;
        }
    }

    @Override
    public int adjustMidiNoteForKeyboard(int baseMidiNote, boolean isUpperKeyboard) {
        return isUpperKeyboard ? baseMidiNote + 12 : baseMidiNote - 12;
    }

    @Override
    public void playScore(List<IScoreEvent> scoreEvents) {
        if (channel == null) {
            System.err.println("Cannot play score on Organ, MIDI channel is not available.");
            return;
        }
        if (scoreEvents == null || scoreEvents.isEmpty()) {
            System.out.println("No score events to play on Organ.");
            return;
        }

        if (scorePlayingThread != null && scorePlayingThread.isAlive()) {
            System.out.println("OrganPlayer: Stopping previous score playback.");
            scorePlayingThread.interrupt();
            try {
                scorePlayingThread.join(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("OrganPlayer starting to play score...");
        scorePlayingThread = new Thread(() -> {
            try {
                for (IScoreEvent event : scoreEvents) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("OrganPlayer playback thread interrupted, stopping score.");
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
                            System.err.println("OrganPlayer playback interrupted during note hold.");
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
                                System.err.println("OrganPlayer playback interrupted during silence.");
                                break;
                            }
                        }
                    }
                }
            } finally {
                System.out.println("OrganPlayer finished playing score or was interrupted.");
            }
        });
        scorePlayingThread.setDaemon(true);
        scorePlayingThread.start();
    }

    @Override
    public void close() {
        System.out.println("OrganPlayer.close() called. Stopping notes and interrupting score thread.");
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
        System.out.println("OrganPlayer resources cleaned. Synthesizer remains open.");
    }

    @Override
    public void addEffect() {
        System.out.println("addEffect not implemented in OrganPlayer.");
    }
}
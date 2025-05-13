package com.pianoo.model;

import javax.sound.midi.*;
import java.util.HashSet;
import java.util.Set;

public class OrganPlayer implements IOrganPlayer {
    private final Set<Integer> activeNotes = new HashSet<>();
    private Synthesizer synth;
    private MidiChannel channel;

    private static final int CHURCH_ORGAN_INSTRUMENT = 19;
    private static final int DEFAULT_VELOCITY = 100;

    public OrganPlayer() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channel = synth.getChannels()[0];
            channel.programChange(CHURCH_ORGAN_INSTRUMENT);
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
    public void stopNote(int midiNote) {
        if (channel != null) {
            channel.noteOff(midiNote);
            activeNotes.remove(midiNote);
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
}
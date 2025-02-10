package com.pianoo.model;

import java.util.HashMap;
import java.util.Map;

public class Frequencies {
    private static final Map<String, Integer> notesMIDI = new HashMap<>();

    static {
        String[] notes = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};

        for (int octave = 0; octave <= 8; octave++) {
            for (int i = 0; i < notes.length; i++) {
                int midiValue = (octave * 12) + i + 12;
                String noteName = notes[i] + octave;
                notesMIDI.put(noteName, midiValue);
            }
        }
    }

    public static int getMidi(String note) {
        return notesMIDI.getOrDefault(note.toUpperCase(), -1);
    }
}

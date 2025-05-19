package com.pianoo.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreReader implements IScoreReader {

    // Basic mapping for note names to MIDI base values (C note in an octave)
    // This will need to be more robust, handling sharps/flats and octaves correctly.
    private static final Map<String, Integer> NOTE_TO_MIDI_BASE = new HashMap<>();
    static {
        NOTE_TO_MIDI_BASE.put("C", 0);
        NOTE_TO_MIDI_BASE.put("C#", 1);
        NOTE_TO_MIDI_BASE.put("DB", 1);
        NOTE_TO_MIDI_BASE.put("D", 2);
        NOTE_TO_MIDI_BASE.put("D#", 3);
        NOTE_TO_MIDI_BASE.put("EB", 3);
        NOTE_TO_MIDI_BASE.put("E", 4);
        NOTE_TO_MIDI_BASE.put("F", 5);
        NOTE_TO_MIDI_BASE.put("F#", 6);
        NOTE_TO_MIDI_BASE.put("GB", 6);
        NOTE_TO_MIDI_BASE.put("G", 7);
        NOTE_TO_MIDI_BASE.put("G#", 8);
        NOTE_TO_MIDI_BASE.put("AB", 8);
        NOTE_TO_MIDI_BASE.put("A", 9);
        NOTE_TO_MIDI_BASE.put("A#", 10);
        NOTE_TO_MIDI_BASE.put("BB", 10);
        NOTE_TO_MIDI_BASE.put("B", 11);
    }

    // Assuming MIDI note 0-127. Let's define silence as -1 for now.
    private static final int SILENCE_MIDI_NOTE = -1;

    @Override
    public List<IScoreEvent> readScore(File scoreFile) throws Exception {
        List<IScoreEvent> scoreEvents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("//") || line.startsWith("#")) { // Skip empty lines and comments
                    continue;
                }

                String[] parts = line.split("\\s+");
                if (parts.length != 2) {
                    System.err.println("Skipping invalid line: " + line);
                    continue;
                }

                String noteName = parts[0].toUpperCase();
                float duration;
                try {
                    duration = Float.parseFloat(parts[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Skipping line with invalid duration: " + line);
                    continue;
                }

                int midiNote;
                if (noteName.equals("0") || noteName.equalsIgnoreCase("Unknown") || noteName.equalsIgnoreCase("SILENCE")) {
                    midiNote = SILENCE_MIDI_NOTE;
                } else {
                    midiNote = parseNoteToMidi(noteName);
                    if (midiNote == -1) { // If parsing failed
                        System.err.println("Skipping unknown note: " + noteName + " in line: " + line);
                        continue;
                    }
                }
                scoreEvents.add(new ScoreEvent(midiNote, duration));
            }
        } catch (IOException e) {
            throw new Exception("Error reading score file: " + scoreFile.getName(), e);
        }
        return scoreEvents;
    }

    /**
     * Parses a note string (e.g., "C4", "A#3") into a MIDI note value.
     * Returns -1 if the note string is invalid.
     */
    private int parseNoteToMidi(String noteString) {
        if (noteString == null || noteString.isEmpty()) {
            System.err.println("Skipping null or empty note string.");
            return -1;
        }

        String noteNamePart;
        int octavePart;

        // Ensure the note string has at least one character for the note name and one for the octave
        if (noteString.length() < 2) {
            System.err.println("Invalid note string (too short, expected format like C4, A#3): " + noteString);
            return -1;
        }

        char lastChar = noteString.charAt(noteString.length() - 1);
        if (Character.isDigit(lastChar)) {
            octavePart = Character.getNumericValue(lastChar);
            noteNamePart = noteString.substring(0, noteString.length() - 1);

            if (noteNamePart.isEmpty()) {
                System.err.println("Invalid note format: Note name part is empty (e.g., found just a number like '4' for note): " + noteString);
                return -1;
            }

            // Vérification pour des cas comme "#4" (où '#' n'est pas une note)
            // Si noteNamePart est juste "#" et n'est pas suivi d'une lettre (ce qui est déjà le cas si length < 2 est géré)
            // ou si NOTE_TO_MIDI_BASE ne le contient pas.
            // La vérification NOTE_TO_MIDI_BASE.containsKey(actualNoteName) ci-dessous s'en chargera.

        } else {
            System.err.println("Octave number missing or invalid character at the end for note: \"" + noteString + "\" (expected a digit).");
            return -1;
        }

        String actualNoteName = noteNamePart.toUpperCase();

        if (!NOTE_TO_MIDI_BASE.containsKey(actualNoteName)) {
            System.err.println("Unknown note name: \"" + actualNoteName + "\" (from original \"" + noteString + "\", was it a typo or not in map?).");
            return -1;
        }

        int baseMidiValue = NOTE_TO_MIDI_BASE.get(actualNoteName);
        // MIDI C4 = 60. Formula: (octave + 1) * 12 + baseNoteValue (where C=0)
        // Assumes octave 0 is the first octave.
        int midiNote = (octavePart + 1) * 12 + baseMidiValue;

        if (midiNote < 0 || midiNote > 127) {
            System.err.println("MIDI note out of range (0-127): " + midiNote + " for note \"" + noteString + "\" (Octave: " + octavePart + ", BaseValue: " + baseMidiValue + ")");
            return -1;
        }
        return midiNote;
    }
}
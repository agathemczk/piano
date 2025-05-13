package com.pianoo.model;

public class Note implements INote {
    private final int noteValue;
    private final int octave;

    public Note(int noteValue, int octave) {
        this.noteValue = noteValue % 12;
        this.octave = octave;
    }

    @Override
    public int getNoteValue() {
        return noteValue;
    }

    @Override
    public int getOctave() {
        return octave;
    }

    @Override
    public int getMidiNote() {
        return (octave + 1) * 12 + noteValue;
    }

    @Override
    public boolean isBlackKey() {
        return noteValue == 1 || noteValue == 3 || noteValue == 6 || noteValue == 8 || noteValue == 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return noteValue == note.noteValue && octave == note.octave;
    }

    @Override
    public int hashCode() {
        return 31 * noteValue + octave;
    }
}
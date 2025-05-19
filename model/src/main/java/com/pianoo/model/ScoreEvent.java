package com.pianoo.model;

public class ScoreEvent implements IScoreEvent {

    private final int midiNote;
    private final float durationSeconds;

    public ScoreEvent(int midiNote, float durationSeconds) {
        this.midiNote = midiNote;
        this.durationSeconds = durationSeconds;
    }

    @Override
    public int getMidiNote() {
        return midiNote;
    }

    @Override
    public float getDurationSeconds() {
        return durationSeconds;
    }

    @Override
    public String toString() {
        return "ScoreEvent{" +
                "midiNote=" + midiNote +
                ", durationSeconds=" + durationSeconds +
                '}';
    }
}
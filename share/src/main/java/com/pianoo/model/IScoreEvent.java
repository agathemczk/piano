package com.pianoo.model;

/**
 * Represents an event in a musical score, like a note or a silence, with a duration.
 */
public interface IScoreEvent {
    /**
     * Gets the MIDI note value for this event.
     * Can be a special value (e.g., -1) to indicate silence.
     *
     * @return The MIDI note value, or a silence indicator.
     */
    int getMidiNote();

    /**
     * Gets the duration of this event in seconds.
     *
     * @return The duration in seconds.
     */
    float getDurationSeconds();
}

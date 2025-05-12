package com.pianoo.model;

/**
 * Représente un événement de note dans une partition.
 */
public class NoteEvent implements INoteEvent{
    private String note;  // Nom de la note (ex: A4, B4, C5)
    private double duration;  // Durée de la note en secondes

    public NoteEvent(String note, double duration) {
        this.note = note;
        this.duration = duration;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public double getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "NoteEvent{" +
                "note='" + note + '\'' +
                ", duration=" + duration +
                '}';
    }
}

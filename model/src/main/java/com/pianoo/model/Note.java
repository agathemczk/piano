package com.pianoo.model;

public class Note {
    private String nom;
    private int midi;
    private double duree; // en secondes

    public Note(String nom, double duree) {
        this.nom = nom;
        this.midi = Frequencies.getMidi(nom);
        this.duree = duree;
    }

    public int getMidi() {
        return midi;
    }

    public int getDureeMs() {
        return (int) (duree * 1000);
    }

    @Override
    public String toString() {
        return nom + " (MIDI: " + midi + ") - " + duree + "s";
    }
}
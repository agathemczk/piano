package com.pianoo.controller;

import javax.sound.midi.*;
import java.util.HashMap;
import java.util.Map;

public class MusicPlayer {
    private Synthesizer synth;
    private MidiChannel channel;
    private Map<String, Integer> instrumentsMap; // Associe un nom d'instrument à un programme MIDI

    public MusicPlayer() {
        try {
            synth = MidiSystem.getSynthesizer();
            synth.open();
            channel = synth.getChannels()[0]; // On utilise un seul canal MIDI

            // Définition des instruments et des sons MIDI correspondants
            instrumentsMap = new HashMap<>();
            instrumentsMap.put("Piano", 0);       // Acoustic Grand Piano
            instrumentsMap.put("Xylophone", 13);  // Xylophone
            instrumentsMap.put("Orgue", 19);      // Church Organ
            instrumentsMap.put("Violon", 40);     // Violin

            // Initialisation avec un instrument par défaut
            changerInstrument("Piano");

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void changerInstrument(String instrument) {
        Integer program = instrumentsMap.get(instrument);
        if (program != null) {
            channel.programChange(program);
            System.out.println("Instrument changé : " + instrument);
        } else {
            System.out.println("Instrument inconnu : " + instrument);
        }
    }

    public void jouerNote(int noteMidi, int dureeMs) {
        if (noteMidi > 0) {
            channel.noteOn(noteMidi, 80); // Volume = 80
        }
        try {
            Thread.sleep(dureeMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (noteMidi > 0) {
            channel.noteOff(noteMidi);
        }
    }

    public void fermer() {
        synth.close();
    }
}
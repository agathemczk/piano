package com.pianoo.controller;

public class Application {
    private MusicPlayer player;
    private String instrumentActuel;
    private Record recordDB;

    public Application() {
        player = new MusicPlayer();
        instrumentActuel = "Piano"; // Instrument par défaut
        recordDB = new Record();
        //test2
        //test3
    }

    public void choisirInstrument(String instrument) {
        instrumentActuel = instrument;
        player.changerInstrument(instrument);
    }

    public void commencerEnregistrement() {
        recordDB.commencerEnregistrement();
    }

    public void arreterEnregistrement(String nomFichier) {
        recordDB.arreterEnregistrement(nomFichier);
    }

    public void jouerNote(String note) {
        long startTime = System.currentTimeMillis();
        int midi = FrequenceNotes.getMidi(note);

        if (midi > 0) {
            player.jouerNote(midi, 500);
        }

        long duree = System.currentTimeMillis() - startTime; // Temps appuyé

        recordDB.ajouterNote(instrumentActuel, note, duree / 1000.0);
    }
}

package com.pianoo.model;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CatPlay implements ICatPlay {

    @Override
    public void playMeowSound() {
        try {
            // Charger le fichier audio
            InputStream audioSrc = getClass().getResourceAsStream("./ressources/audio_cat/Meow.mp3");
            if (audioSrc == null) {
                System.err.println("Fichier audio Meow.mp3 introuvable");
                return;
            }

            // Utiliser un BufferedInputStream pour améliorer les performances
            InputStream bufferedIn = new BufferedInputStream(audioSrc);

            // Utiliser JavaFX Media Player ou Java Sound API selon le format
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            } catch (UnsupportedAudioFileException e) {
                System.err.println("Format audio non supporté: " + e.getMessage());
            }

        } catch (IOException | LineUnavailableException e) {
            System.err.println("Erreur lors de la lecture du son: " + e.getMessage());
        }
    }
}
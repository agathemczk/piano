package com.pianoo.model;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CatPlay implements ICatPlay {
    private Clip currentClip; // Pour stocker la référence au son en cours

    @Override
    public void playMeowSound() {
        try {
            // Arrêter le son précédent si encore actif
            stopSound();

            InputStream audioSrc = getClass().getResourceAsStream("/cat_audio/Meow.wav");
            if (audioSrc == null) {
                System.err.println("Fichier audio introuvable");
                return;
            }

            BufferedInputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);

            currentClip = AudioSystem.getClip();
            currentClip.open(audioInputStream);
            currentClip.start();

        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture du son: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void stopSound() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.close();
        }
    }
}
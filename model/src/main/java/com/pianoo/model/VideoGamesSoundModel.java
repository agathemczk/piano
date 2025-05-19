package com.pianoo.model;

import javax.sound.sampled.*;
import java.util.HashMap;
import java.util.Map;

public class VideoGamesSoundModel implements IVideoGamesSoundModel {

    private static final float SAMPLE_RATE = 22050f;
    private static final int SAMPLE_SIZE_IN_BITS = 8;
    private static final int CHANNELS = 1;
    private static final boolean SIGNED = true;
    private static final boolean BIG_ENDIAN = false;
    private static final double DEFAULT_DURATION_SECONDS = 0.3;

    private AudioFormat audioFormat;
    private Map<String, Double> noteFrequencies;

    public VideoGamesSoundModel() {
        audioFormat = new AudioFormat(SAMPLE_RATE, SAMPLE_SIZE_IN_BITS, CHANNELS, SIGNED, BIG_ENDIAN);
        initializeNoteFrequencies();
    }

    private void initializeNoteFrequencies() {
        noteFrequencies = new HashMap<>();
        noteFrequencies.put("C", 261.63);
        noteFrequencies.put("D", 293.66);
        noteFrequencies.put("E", 329.63);
        noteFrequencies.put("F", 349.23);
        noteFrequencies.put("G", 392.00);
        noteFrequencies.put("A", 440.00);
        noteFrequencies.put("B", 493.88);
    }

    @Override
    public void playNote(String noteName) {
        new Thread(() -> playSoundInternal(noteName, DEFAULT_DURATION_SECONDS)).start();
    }

    @Override
    public void playNote(String noteName, double durationSeconds) {
        playSoundInternal(noteName, durationSeconds);
    }

    private void playSoundInternal(String noteName, double durationSeconds) {
        Double frequency = noteFrequencies.get(noteName.toUpperCase());
        if (frequency == null) {
            System.err.println("Fréquence non définie pour la note : " + noteName);
            return;
        }
        if (durationSeconds <= 0) {
            return;
        }

        SourceDataLine line = null;
        try {
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
            if (!AudioSystem.isLineSupported(info)) {
                System.err.println("Ligne non supportée: " + info);
                return;
            }
            line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(audioFormat);
            line.start();

            int numSamples = (int) (durationSeconds * SAMPLE_RATE);
            if (numSamples == 0 && durationSeconds > 0) {
                numSamples = 1;
            }
            if (numSamples == 0) return;

            byte[] buffer = new byte[numSamples];

            for (int i = 0; i < numSamples; i++) {
                double time = i / SAMPLE_RATE;
                double amplitudeValue = 100.0;
                buffer[i] = (byte) (amplitudeValue * Math.sin(2 * Math.PI * frequency * time));
            }

            line.write(buffer, 0, buffer.length);
            line.drain();

        } catch (LineUnavailableException e) {
            System.err.println("Erreur de ligne audio : " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (line != null) {
                line.close();
            }
        }
    }
}
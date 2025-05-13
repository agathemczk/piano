package com.pianoo.model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RecordPlayer implements IRecordPlayer {
    private boolean isRecording = false;
    private BufferedWriter writer;
    private static final String RECORDINGS_DIR = "consignes/partitions";
    private long lastEventTimestamp = 0;

    // Pour suivre les notes actuellement pressées et leur heure de début
    private Map<String, Long> activeNotes = new HashMap<>();

    public RecordPlayer() {
        File dir = new File(RECORDINGS_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public boolean startRecording(String filename) {
        if (isRecording) {
            System.err.println("Déjà en cours d'enregistrement.");
            return false;
        }
        try {
            String sanitizedFilename = filename.trim().replaceAll("\\s+", "_");
            if (sanitizedFilename.isEmpty()) {
                System.err.println("Le nom de fichier ne peut pas être vide.");
                return false;
            }

            File fileToSave = new File(RECORDINGS_DIR, sanitizedFilename + ".txt");

            if (fileToSave.exists()) {
                System.err.println("Le fichier '" + fileToSave.getName() + "' existe déjà. Veuillez choisir un autre nom.");
                return false;
            }

            writer = new BufferedWriter(new FileWriter(fileToSave));
            isRecording = true;
            lastEventTimestamp = System.currentTimeMillis();
            activeNotes.clear();
            System.out.println("Enregistrement démarré dans le fichier : " + fileToSave.getAbsolutePath());
            return true;
        } catch (IOException e) {
            System.err.println("Erreur au démarrage de l'enregistrement : " + e.getMessage());
            isRecording = false;
            return false;
        }
    }

    @Override
    public void stopRecording() {
        if (!isRecording) {
            return;
        }
        try {
            // Gérer les notes qui étaient encore actives au moment de l'arrêt
            long stopTime = System.currentTimeMillis();
            for (Map.Entry<String, Long> entry : activeNotes.entrySet()) {
                writeNoteDuration(entry.getKey(), entry.getValue(), stopTime);
            }
            activeNotes.clear();

            if (writer != null) {
                writer.close();
            }
            isRecording = false;
            System.out.println("Enregistrement arrêté.");
        } catch (IOException e) {
            System.err.println("Erreur à l'arrêt de l'enregistrement : " + e.getMessage());
        } finally {
            writer = null;
        }
    }

    @Override
    public void recordNoteOn(String noteName, long timestamp) {
        if (!isRecording || writer == null) return;

        // Enregistrer le silence avant cette note
        if (lastEventTimestamp > 0 && timestamp > lastEventTimestamp) {
            writeSilenceDuration(timestamp - lastEventTimestamp);
        }

        activeNotes.put(noteName, timestamp);
        lastEventTimestamp = timestamp; // Mettre à jour pour le prochain silence ou la fin de cette note
    }

    @Override
    public void recordNoteOff(String noteName, long timestamp) {
        if (!isRecording || writer == null || !activeNotes.containsKey(noteName)) return;

        long startTime = activeNotes.remove(noteName);

        // Écrire la durée de la note
        writeNoteDuration(noteName, startTime, timestamp);

        lastEventTimestamp = timestamp; // Mettre à jour le timestamp du dernier événement
    }

    private void writeNoteDuration(String noteName, long startTime, long endTime) {
        if (writer == null) return;
        try {
            double durationSeconds = (endTime - startTime) / 1000.0;
            writer.write(noteName + " " + String.format("%.3f", durationSeconds));
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Erreur d'écriture (note) : " + e.getMessage());
        }
    }

    private void writeSilenceDuration(long durationMillis) {
        if (writer == null || durationMillis <= 0) return; // Ne pas écrire de silence de durée nulle ou négative
        try {
            double durationSeconds = durationMillis / 1000.0;
            writer.write("0 " + String.format("%.3f", durationSeconds));
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.err.println("Erreur d'écriture (silence) : " + e.getMessage());
        }
    }

    @Override
    public boolean isRecording() {
        return isRecording;
    }
}

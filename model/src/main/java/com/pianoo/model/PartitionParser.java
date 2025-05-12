package com.pianoo.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation du parseur de partition pour fichiers texte.
 */

public class PartitionParser implements IPartitionParser {
    @Override
    public List<INoteEvent> parseScore(String filePath) throws Exception {
        List<INoteEvent> notes = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Ignorer les lignes vides ou de commentaires
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // Séparer la note et la durée
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 2) {
                    String note = parts[0];
                    double duration = Double.parseDouble(parts[1]);
                    notes.add(new NoteEvent(note, duration));
                }
            }
        }

        return notes;
    }
}

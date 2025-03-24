package com.pianoo.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PartitionLoader {
    public static Partition chargerPartition(String cheminFichier) {
        Partition partition = new Partition();


        try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                ligne = ligne.trim();
                if (!ligne.isEmpty() && !ligne.equals("0")) {
                    String[] parts = ligne.split(" ");
                    if (parts.length == 2) {
                        String nomNote = parts[0];
                        double duree = Double.parseDouble(parts[1]);
                        partition.ajouterNote(new Note(nomNote, duree));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return partition;
    }
}

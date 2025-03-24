package com.pianoo.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Record {
    private boolean enregistrementActif;
    private List<String> notesEnregistrees;

    public Record() {
        enregistrementActif = false;
        notesEnregistrees = new ArrayList<>();
    }

    // Démarrer l'enregistrement
    public void commencerEnregistrement() {
        enregistrementActif = true;
        notesEnregistrees.clear();
        System.out.println("Enregistrement commencé...");
    }

    // Arrêter l'enregistrement et sauvegarder le fichier
    public void arreterEnregistrement(String nomFichier) {
        if (!enregistrementActif) {
            System.out.println("Aucun enregistrement en cours.");
            return;
        }

        enregistrementActif = false;

        // Convertir la liste en texte
        StringBuilder sb = new StringBuilder();
        for (String note : notesEnregistrees) {
            sb.append(note).append("\n");
        }

        // Vérification si le fichier existe déjà
        Scanner scanner = new Scanner(System.in);
        Path cheminFichier = Paths.get("enregistrements/" + nomFichier + ".txt");

        while (Files.exists(cheminFichier)) {
            System.out.print("Le fichier existe déjà. Entrez un autre nom : ");
            nomFichier = scanner.nextLine();
            cheminFichier = Paths.get("enregistrements/" + nomFichier + ".txt");
        }

        // Sauvegarde du fichier
        try {
            Files.write(cheminFichier, sb.toString().getBytes());
            System.out.println("Enregistrement sauvegardé sous '" + nomFichier + ".txt'");
        } catch (IOException e) {
            System.out.println("Erreur lors de la sauvegarde du fichier.");
            e.printStackTrace();
        }
    }

    // Ajouter une note pendant l'enregistrement
    public void ajouterNote(String instrument, String note, double duree) {
        if (enregistrementActif) {
            notesEnregistrees.add(instrument + " - " + note + " (" + duree + "s)");
        }
    }

    // Vérifier si l'enregistrement est actif
    public boolean isEnregistrementActif() {
        return enregistrementActif;
    }
}
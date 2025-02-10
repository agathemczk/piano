package com.pianoo;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*Partition partition = PartitionLoader.chargerPartition("star_wars.txt");
        partition.afficher();   lire et afficher la partition*/


        Application app = new Application();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu :");
            System.out.println("1 - Changer instrument");
            System.out.println("2 - Jouer note");
            System.out.println("3 - Commencer un enregistrement");
            System.out.println("4 - Arrêter l'enregistrement et sauvegarder");
            System.out.println("5 - Quitter");

            System.out.print("Faites un choix : ");

            int choix;
            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrée invalide, veuillez entrer un nombre.");
                continue;
            }

            if (choix == 1) {
                System.out.println("Instruments disponibles : Piano, Xylophone, Orgue, Violon");
                System.out.print("Choisissez un instrument : ");
                String instrument = scanner.nextLine();
                app.choisirInstrument(instrument);
            }
            else if (choix == 2) {
                System.out.print("Entrez une note (ex: C4, A#2) : ");
                String note = scanner.nextLine();
                app.jouerNote(note);
            }
            else if (choix == 3) {
                app.commencerEnregistrement();
            }
            else if (choix == 4) {
                System.out.print("Entrez le nom du fichier d'enregistrement : ");
                String nomFichier = scanner.nextLine();
                app.arreterEnregistrement(nomFichier);
            }
            else if (choix == 5) {
                System.out.println("Fermeture de l'application...");
                break;
            }
            else {
                System.out.println("Choix invalide, veuillez entrer un nombre entre 1 et 5.");
            }
        }
        scanner.close();
    }
}
package com.pianoo.view;

import java.io.File;

/**
 * Interface pour la vue de lecture de partition.
 */
public interface IPartitionMenu {
    /**
     * Affiche un sélecteur de fichier pour choisir une partition.
     *
     * @return Fichier de partition sélectionné, ou null si aucun fichier n'est choisi
     */
    File showScoreFileChooser();

    /**
     * Affiche un message d'erreur.
     *
     * @param message Message d'erreur à afficher
     */
    void showErrorMessage(String message);
}
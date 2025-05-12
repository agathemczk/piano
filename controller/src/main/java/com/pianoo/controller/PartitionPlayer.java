package com.pianoo.controller;

import com.pianoo.model.INoteEvent;
import com.pianoo.model.IPartitionParser;
import com.pianoo.view.IPartitionMenu;

import java.io.File;
import java.util.List;

/**
 * Contrôleur pour la fonctionnalité de lecture de partition.
 */

public class PartitionPlayer implements IPartitionPlayer {
    private IPartitionParser parser;
    private IPartitionPlayer player;
    private IPartitionMenu view;

    public PartitionPlayer(IPartitionParser parser, IPartitionPlayer player, IPartitionMenu view) {
        this.parser = parser;
        this.player = player;
        this.view = view;
    }

    /**
     * Gère l'ouverture et la lecture d'un fichier de partition.
     *
     * @param instrument Type de l'instrument actuellement sélectionné
     */
    public void openAndPlayScore(String instrument) {
        try {
            // Ouvrir le fichier de partition
            File file = view.showScoreFileChooser();
            if (file != null) {
                // Parser le fichier pour obtenir les notes
                List<INoteEvent> notes = parser.parseScore(file.getAbsolutePath());
                // Jouer les notes
                player.playScore(notes);
            }
        } catch (Exception e) {
            view.showErrorMessage("Erreur lors de la lecture de la partition : " + e.getMessage());
        }
    }


    @Override
    public void playScore(final List<INoteEvent> notes) throws Exception {
    }
}

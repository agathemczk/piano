package com.pianoo.controller;

import com.pianoo.model.INoteEvent;

import java.util.List;
/**
 * Interface pour lire et jouer des partitions.
 */

public interface IPartitionPlayer {
    /**
     * Joue une liste d'événements de notes.
     *
     * @param notes Liste des événements de notes à jouer
     * @throws Exception En cas d'erreur lors de la lecture
     */
    void playScore(List<INoteEvent> notes) throws Exception;
}
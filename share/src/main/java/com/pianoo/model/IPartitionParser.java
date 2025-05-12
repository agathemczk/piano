package com.pianoo.model;
import java.util.List;

public interface IPartitionParser {
    /**
     * Parse une partition à partir d'un fichier texte.
     *
     * @param filePath Chemin du fichier de partition
     * @return Liste de notes à jouer
     * @throws Exception En cas d'erreur de lecture ou de parsing
     */
    List<INoteEvent> parseScore(String filePath) throws Exception;
}

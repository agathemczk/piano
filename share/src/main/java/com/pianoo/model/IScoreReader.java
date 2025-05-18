package com.pianoo.model;

import java.util.List;
import java.io.File;

/**
 * Interface for reading music scores.
 */
public interface IScoreReader {

    /**
     * Reads a score file and returns a list of notes to be played.
     * Each note could be represented by an object or a simple structure
     * containing pitch and duration.
     *
     * @param scoreFile The file containing the score.
     * @return A list of INote objects representing the score.
     * @throws Exception If an error occurs during file reading or parsing.
     */
    List<IScoreEvent> readScore(File scoreFile) throws Exception;
}

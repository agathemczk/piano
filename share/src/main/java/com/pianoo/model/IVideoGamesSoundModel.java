package com.pianoo.model;

public interface IVideoGamesSoundModel {
    /**
     * Joue une note correspondant au nom donné (par exemple "DO", "RÉ", etc.).
     * L'implémentation sera responsable de mapper ce nom à une fréquence
     * et de générer le son 8-bit.
     *
     * @param noteName Le nom de la note à jouer.
     */
    void playNote(String noteName);

    // On pourrait ajouter d'autres méthodes plus tard si nécessaire, par exemple :
    // void stopNote(String noteName); // Si les sons peuvent être tenus
    // void setVolume(double volume);
    // void close(); // Pour libérer les ressources si le modèle en utilise (ex: AudioSystem)
}
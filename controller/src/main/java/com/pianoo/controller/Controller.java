package com.pianoo.controller;

import com.pianoo.model.*;
import com.pianoo.view.*;
import javax.swing.JOptionPane;
import java.io.File;
import java.util.List;

public class Controller implements IController, IOnChoiceSelectedListener, IMenuNavigationListener, ICatListener {

    // Déclarations des champs finaux (s'assurer qu'ils correspondent aux paramètres du constructeur)
    private final IMusicPlayer musicPlayer;
    private final IXylophonePlayer xylophonePlayer;
    private final IDrumsPlayer drumsPlayer;
    private final IOrganPlayer organPlayer;
    private final IRecordPlayer recordPlayer;
    private final IVideoGamesSoundModel videoGamesSoundModel;
    private final IKeyboardMapping keyboardMapping;
    private final ICatPlay catPlay;
    private final IMainMenu mainMenu; // mainMenu peut aussi être final s'il est fixé à la construction
    private final IScoreReader scoreReader; // Added field
    private IScoreChooserView scoreChooserView; // Added field (can be set if it's a dialog)
    private Thread scorePlaybackThread = null; // Pour suivre le thread de lecture de partition
    private String activeInstrumentForScorePlayback = "Piano"; // Nouvel attribut pour suivre l'instrument actif

    // Champs pour les frames (non final car peuvent être set via setters ou initialisés après)
    private IPianoFrame pianoFrame;
    private IOrganFrame organFrame;
    private IXylophoneFrame xylophoneFrame;
    private IVideoGamesFrame videoGamesFrame;
    private IDrumsFrame drumsFrame;
    private ICatFrame catFrame;

    // Constructeur mis à jour
    public Controller(IMusicPlayer musicPlayer, IXylophonePlayer xylophonePlayer, IDrumsPlayer drumsPlayer,
                      IOrganPlayer organPlayer, IRecordPlayer recordPlayer, IVideoGamesSoundModel videoGamesSoundModel,
                      IMainMenu mainMenu, IPianoFrame pianoFrame, IOrganFrame organFrame,
                      IXylophoneFrame xylophoneFrame, IVideoGamesFrame videoGamesFrame, IDrumsFrame drumsFrame,
                      ICatFrame catFrame, ICatPlay catPlay, IKeyboardMapping keyboardMapping,
                      IScoreReader scoreReader, IScoreChooserView scoreChooserView) { // Added parameters
        // Assignation des champs final
        this.musicPlayer = musicPlayer;
        this.xylophonePlayer = xylophonePlayer;
        this.drumsPlayer = drumsPlayer;
        this.organPlayer = organPlayer;
        this.recordPlayer = recordPlayer;
        this.videoGamesSoundModel = videoGamesSoundModel;
        this.mainMenu = mainMenu; // Assignation de mainMenu
        this.catPlay = catPlay;
        this.keyboardMapping = keyboardMapping;
        this.scoreReader = scoreReader; // Added assignment
        this.scoreChooserView = scoreChooserView; // Added assignment

        // Assignation/Configuration des frames (qui ne sont pas final)
        this.pianoFrame = pianoFrame;
        this.organFrame = organFrame;
        this.xylophoneFrame = xylophoneFrame;
        this.videoGamesFrame = videoGamesFrame;
        this.drumsFrame = drumsFrame;
        this.catFrame = catFrame;

        // Configuration des listeners etc.
        if (this.mainMenu != null) { // Ajout d'une vérification pour mainMenu
            this.mainMenu.setInstrumentSelectedListener(this);
            this.mainMenu.setVisible(true);
        }

        if (this.pianoFrame != null) {
            this.pianoFrame.setListener(this);
            this.pianoFrame.setController(this);
        }
        if (this.organFrame != null) {
            this.organFrame.setListener(this);
            this.organFrame.setController(this);
        }
        if (this.xylophoneFrame != null) {
            this.xylophoneFrame.setListener(this);
            this.xylophoneFrame.setController(this);
        }
        if (this.videoGamesFrame != null) {
            this.videoGamesFrame.setListener(this);
            this.videoGamesFrame.setController(this);
        }
        if (this.drumsFrame != null) {
            this.drumsFrame.setListener(this);
            this.drumsFrame.setController(this);
        }
        if (this.catFrame != null) {
            this.catFrame.setListener(this);
            this.catFrame.setCatPlayListener(this);
            // Si CatFrame a besoin d'un setController générique:
            // if (this.catFrame instanceof SomeInterfaceWithSetController) {
            // ((SomeInterfaceWithSetController)this.catFrame).setController(this);
            // }
        }
    }

    @Override
    public void onInstrumentSelected(String instrumentName) {
        if ("Piano".equals(instrumentName)) {
            openPiano();
        }
        if ("Xylophone".equals(instrumentName)) {
            openXylophone();
        }
        if ("VideoGames".equals(instrumentName)) {
            openVideoGames();
        }
        if ("Organ".equals(instrumentName)) {
            openOrgan();
        }
        if ("Drums".equals(instrumentName)) {
            openDrums();
        }
        if ("Cat".equals(instrumentName)) {
            openCat();
        }
        if ("PlayPartition".equals(instrumentName)) { // Added case for playing partition
            handlePlayScore();
        }
    }

    @Override
    public void onReturnMainMenu() {
        stopScorePlayback(); // Arrêter la lecture de la partition
        if (catPlay != null) {
            catPlay.stopSound();
        }
        if (recordPlayer != null && recordPlayer.isRecording()) {
            recordPlayer.stopRecording();
            updateAllRecordButtonsState(false);
        }
        showMainMenuScreen(); // Calls the private method
    }

    @Override
    public void showMainMenu() {
        stopScorePlayback(); // Arrêter la lecture de la partition
        if (catPlay != null) {
            catPlay.stopSound();
        }
        if (recordPlayer != null && recordPlayer.isRecording()) {
            recordPlayer.stopRecording();
            updateAllRecordButtonsState(false);
        }
        showMainMenuScreen(); // Calls the private method
    }

    private void stopScorePlayback() {
        if (scorePlaybackThread != null && scorePlaybackThread.isAlive()) {
            scorePlaybackThread.interrupt(); // Demande au thread de s'arrêter
            try {
                scorePlaybackThread.join(1000); // Attend un peu que le thread se termine
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Rétablit le statut d'interruption
            }
        }
        if (musicPlayer != null) {
            // musicPlayer.silenceAllNotes(); // Sera décommenté après modification de IMusicPlayer
        }
    }

    private void showMainMenuScreen() {
        if (mainMenu == null || mainMenu.getContentPane() == null) {
            System.err.println("MainMenu ou son content pane n'est pas initialisé.");
            return;
        }
        mainMenu.getContentPane().removeAll();
        mainMenu.initializeUI(); // Make sure this recreates buttons including Play Partition
        mainMenu.revalidate();
        mainMenu.repaint();
    }

    private void updateAllRecordButtonsState(boolean isRecording) {
        if (pianoFrame != null) {
            pianoFrame.updateRecordButtonState(isRecording);
        }
        if (organFrame != null) {
            organFrame.updateRecordButtonState(isRecording);
        }
        if (xylophoneFrame != null) {
            xylophoneFrame.updateRecordButtonState(isRecording);
        }
        if (videoGamesFrame != null) {
            videoGamesFrame.updateRecordButtonState(isRecording);
        }
    }

    private void openPiano() {
        if (mainMenu == null || mainMenu.getContentPane() == null || pianoFrame == null || pianoFrame.getPanel() == null)
            return;
        activeInstrumentForScorePlayback = "Piano"; // Définir l'instrument actif
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(pianoFrame.getPanel());
        pianoFrame.setKeyListener(this);
        mainMenu.revalidate();
        mainMenu.repaint();
        pianoFrame.getPanel().requestFocusInWindow();
    }

    private void openXylophone() {
        if (mainMenu == null || mainMenu.getContentPane() == null || xylophoneFrame == null || xylophoneFrame.getPanel() == null)
            return;
        activeInstrumentForScorePlayback = "Xylophone"; // Définir l'instrument actif
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(xylophoneFrame.getPanel());
        xylophoneFrame.setKeyListener(this);
        mainMenu.revalidate();
        mainMenu.repaint();
        xylophoneFrame.getPanel().requestFocusInWindow();
    }

    private void openVideoGames() {
        if (mainMenu == null || mainMenu.getContentPane() == null || videoGamesFrame == null || videoGamesFrame.getPanel() == null)
            return;
        activeInstrumentForScorePlayback = "VideoGames"; // Définir l'instrument actif
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(videoGamesFrame.getPanel());
        mainMenu.revalidate();
        mainMenu.repaint();
        videoGamesFrame.getPanel().requestFocusInWindow();
    }

    private void openOrgan() {
        if (mainMenu == null || mainMenu.getContentPane() == null || organFrame == null || organFrame.getPanel() == null)
            return;
        activeInstrumentForScorePlayback = "Organ"; // Définir l'instrument actif
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(organFrame.getPanel());
        organFrame.setController(this);
        mainMenu.revalidate();
        mainMenu.repaint();
        organFrame.getPanel().requestFocusInWindow();
    }

    private void openDrums() {
        if (mainMenu == null || mainMenu.getContentPane() == null || drumsFrame == null || drumsFrame.getPanel() == null)
            return;
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(drumsFrame.getPanel());
        drumsFrame.setController(this);
        mainMenu.revalidate();
        mainMenu.repaint();
        drumsFrame.getPanel().requestFocusInWindow();
    }

    private void openCat() {
        if (mainMenu == null || mainMenu.getContentPane() == null || catFrame == null || catFrame.getPanel() == null)
            return;
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(catFrame.getPanel());
        mainMenu.revalidate();
        mainMenu.repaint();
    }

    private void handlePlayScore() {
        if (scoreChooserView == null || scoreReader == null || musicPlayer == null || mainMenu == null) {
            System.err.println("Score components not initialized properly.");
            JOptionPane.showMessageDialog(null, "La fonctionnalité de lecture de partition n'est pas prête.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Assuming mainMenu is a Frame or can provide one
        if (mainMenu instanceof java.awt.Frame) {
            scoreChooserView.setOwner((java.awt.Frame) mainMenu);
        } else {
            // Fallback or error if mainMenu is not a Frame and cannot provide one.
            // For simplicity, we might need to pass the main Frame to the controller
            // or ensure mainMenu is always a Frame.
            // For now, let's assume it can be owned by null if necessary, or needs a specific owner.
            // If ScoreChooserView constructor takes owner, this call might be more about re-setting/updating if needed.
            // The current ScoreChooserView takes owner in constructor.
            // This line might be more about re-setting/updating if needed.
            // scoreChooserView.setOwner(null); // Or a default frame
        }

        scoreChooserView.displayView(); // This will block until the dialog is closed
        File selectedScoreFile = scoreChooserView.getSelectedScoreFile();

        if (selectedScoreFile != null) {
            try {
                List<IScoreEvent> scoreEvents = scoreReader.readScore(selectedScoreFile);
                if (scoreEvents != null && !scoreEvents.isEmpty()) {
                    stopScorePlayback(); // Arrête toute lecture précédente avant d'en lancer une nouvelle
                    scorePlaybackThread = new Thread(() -> playScoreEvents(scoreEvents));
                    scorePlaybackThread.start();
                } else {
                    JOptionPane.showMessageDialog((java.awt.Component) mainMenu, "La partition est vide ou n'a pas pu être lue.", "Partition Vide", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog((java.awt.Component) mainMenu,
                        "Erreur lors de la lecture de la partition : " + e.getMessage(),
                        "Erreur de Lecture", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void playScoreEvents(List<IScoreEvent> events) {
        final int SILENCE_MIDI_NOTE = -1; // As defined in ScoreReader or a shared constant
        final int DEFAULT_ORGAN_VELOCITY = 100;

        // Mapping simplifié de l'index MIDI (0-11) aux noms de notes Xylophone
        // Ce mapping est une approximation et pourrait nécessiter des ajustements
        // basés sur les notes exactes disponibles pour votre XylophonePlayer.
        String[] xylophoneNoteNames = {
                "C", "Db_Eb", "D", "Db_Eb", "E", "F",
                "Gb_Ab", "G", "Gb_Ab", "A", "Bb", "B"
        };

        // Mapping de l'index MIDI (0-11) aux noms de notes pour VideoGamesSoundModel
        String[] gameNoteMapping = {
                "C", "C", "D", "D", "E", "F", "F", "G", "G", "A", "A", "B"
        }; // C#, D#, F#, G#, A# sont mappés à la note naturelle inférieure

        try {
            for (IScoreEvent event : events) {
                if (Thread.currentThread().isInterrupted()) { // Vérifier l'interruption avant chaque note
                    System.out.println("Score playback interrupted.");
                    break;
                }
                long durationMillis = (long) (event.getDurationSeconds() * 1000);
                if (durationMillis <= 0) continue; // Skip invalid durations

                int midiNote = event.getMidiNote();

                if (midiNote != SILENCE_MIDI_NOTE) {
                    switch (activeInstrumentForScorePlayback) {
                        case "Organ":
                            if (organPlayer != null) {
                                organPlayer.playNote(midiNote, DEFAULT_ORGAN_VELOCITY);
                                Thread.sleep(durationMillis);
                                organPlayer.stopNote(midiNote);
                            }
                            break;
                        case "Xylophone":
                            if (xylophonePlayer != null) {
                                int noteIndex = midiNote % 12; // Obtenir l'index de la note (0-11)
                                String xylophoneNoteName = xylophoneNoteNames[noteIndex];
                                // Pour le xylophone, la durée de la note est souvent intrinsèque au son.
                                // La durée de la partition contrôle l'intervalle jusqu'à la prochaine note.
                                if (xylophoneFrame != null) {
                                    xylophonePlayer.playNote(xylophoneNoteName, xylophoneFrame.getNotes());
                                } else {
                                    // Fallback si xylophoneFrame n'est pas disponible.
                                    // Jouer sans animation ou logger une erreur.
                                    xylophonePlayer.playNote(xylophoneNoteName, null);
                                    System.err.println("XylophoneFrame non disponible pour la lecture de partition, animation des touches impossible.");
                                }
                                Thread.sleep(durationMillis);
                                // XylophonePlayer n'a pas de stopNote explicite, le son s'arrête naturellement.
                            }
                            break;
                        case "VideoGames":
                            if (videoGamesSoundModel != null) {
                                int noteIndex = midiNote % 12;
                                String gameNoteName = gameNoteMapping[noteIndex];
                                videoGamesSoundModel.playNote(gameNoteName, event.getDurationSeconds());
                                // Pas besoin de Thread.sleep ici, car playNote dans VideoGamesSoundModel
                                // est maintenant bloquant pour la durée de la note grâce à line.drain().
                            }
                            break;
                        case "Piano":
                        default:
                            if (musicPlayer != null) {
                                musicPlayer.playNote(midiNote);
                                Thread.sleep(durationMillis);
                                musicPlayer.stopNote(midiNote);
                            }
                            break;
                    }
                } else {
                    Thread.sleep(durationMillis); // Silence
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interruption status
            System.err.println("Playback interrupted forcefully.");
        } finally {
            // Si une méthode silenceAllNotes existait pour chaque player, on pourrait les appeler ici
            // pour s'assurer que tout est silencieux en cas d'erreur ou d'interruption inattendue.
            // Cependant, la gestion actuelle dans stopScorePlayback() devrait déjà s'en charger pour le musicPlayer.
            // Pour organPlayer, l'arrêt de la note est dans la boucle. Xylophone n'a pas de stop.
        }
    }

    @Override
    public void setPianoFrame(final IPianoFrame pianoFrame) {
        this.pianoFrame = pianoFrame;
        if (this.pianoFrame != null) {
            this.pianoFrame.setController(this);
        }
    }

    @Override
    public void setXylophoneFrame(final IXylophoneFrame xylophoneFrame) {
        this.xylophoneFrame = xylophoneFrame;
        if (this.xylophoneFrame != null) {
            this.xylophoneFrame.setController(this);
        }
    }

    // Removed setKeyboardMapping as keyboardMapping is final

    public void onDrumHit(String drumType) {
        if (drumsPlayer != null) drumsPlayer.playDrum(drumType);
        if (recordPlayer != null && recordPlayer.isRecording()) {
            // recordPlayer.recordEvent("Drums: DrumHit, Type=" + drumType);
        }
    }

    @Override
    public void onKeyPressed(int noteValue, int octave) {
        if (musicPlayer == null) return;
        String noteName = musicPlayer.getNoteName(noteValue, octave);
        int midiNote = musicPlayer.getMidiNote(octave, noteValue);
        musicPlayer.playNote(midiNote);
        if (recordPlayer != null && recordPlayer.isRecording()) {
            recordPlayer.recordNoteOn(noteName, System.currentTimeMillis());
        }
    }

    @Override
    public void onKeyReleased(int noteValue, int octave) {
        if (musicPlayer == null) return;
        String noteName = musicPlayer.getNoteName(noteValue, octave);
        int midiNote = musicPlayer.getMidiNote(octave, noteValue);
        musicPlayer.stopNote(midiNote);
        if (recordPlayer != null && recordPlayer.isRecording()) {
            recordPlayer.recordNoteOff(noteName, System.currentTimeMillis());
        }
    }

    @Override
    public void onOrganKeyPressed(int midiNote) {
        organPlayer.playNote(midiNote, 100);
        if (recordPlayer.isRecording()) {
            // Supposons que organPlayer a maintenant getNoteNameFromMidi(midiNote)
            String noteName = organPlayer.getNoteNameFromMidi(midiNote); // À AJOUTER à IOrganPlayer
            recordPlayer.recordNoteOn(noteName, System.currentTimeMillis());
        }
    }

    @Override
    public void onOrganKeyReleased(int midiNote) {
        if (organPlayer == null) return;
        organPlayer.stopNote(midiNote);
        if (recordPlayer != null && recordPlayer.isRecording()) {
            String noteName = organPlayer.getNoteNameFromMidi(midiNote);
            recordPlayer.recordNoteOff(noteName, System.currentTimeMillis());
        }
    }

    @Override
    public void onNotePlayed(final String note) {
        if (xylophonePlayer != null && xylophoneFrame != null) {
            xylophonePlayer.playNote(note, xylophoneFrame.getNotes());
        }
        if (recordPlayer != null && recordPlayer.isRecording()) {
            long currentTime = System.currentTimeMillis();
            recordPlayer.recordNoteOn(note, currentTime);
            recordPlayer.recordNoteOff(note, currentTime + 100);
        }
    }

    @Override
    public int getMidiNoteFromKeyName(String noteName) {
        return organPlayer != null ? organPlayer.getMidiNoteFromKeyName(noteName) : -1;
    }

    @Override
    public boolean isNoteActive(int midiNote) {
        return organPlayer != null ? organPlayer.isNoteActive(midiNote) : false;
    }

    @Override
    public int adjustMidiNoteForKeyboard(int baseMidiNote, boolean isUpperKeyboard) {
        return organPlayer != null ? organPlayer.adjustMidiNoteForKeyboard(baseMidiNote, isUpperKeyboard) : baseMidiNote;
    }

    @Override
    public int getMidiNoteForKeyCode(int keyCode) {
        return organPlayer != null ? organPlayer.getMidiNoteForKeyCode(keyCode) : -1;
    }

    @Override
    public void onPlayCat() {
        if (catPlay != null) catPlay.playMeowSound();
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void toggleRecording() {
        if (recordPlayer == null) return;
        if (recordPlayer.isRecording()) {
            recordPlayer.stopRecording();
        } else {
            boolean recordingAttemptSuccessful = false;
            while (!recordingAttemptSuccessful) {
                String filename = JOptionPane.showInputDialog(null, "Entrez le nom du fichier pour l'enregistrement :", "Nom de l'enregistrement", JOptionPane.PLAIN_MESSAGE);

                if (filename == null) {
                    System.out.println("Saisie du nom de fichier annulée par l'utilisateur.");
                    updateAllRecordButtonsState(false);
                    return;
                }

                if (!filename.trim().isEmpty()) {
                    if (recordPlayer.startRecording(filename)) {
                        recordingAttemptSuccessful = true;
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Le nom de fichier est déjà utilisé ou une erreur est survenue.\nVeuillez choisir un autre nom.",
                                "Erreur de nom de fichier",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    System.out.println("Le nom de fichier ne peut pas être vide.");
                    JOptionPane.showMessageDialog(null,
                            "Le nom de fichier ne peut pas être vide.",
                            "Erreur de nom de fichier",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        updateAllRecordButtonsState(recordPlayer.isRecording());
    }

    @Override
    public void onVideoGameNotePressed(String noteName) {
        if (videoGamesSoundModel != null) {
            videoGamesSoundModel.playNote(noteName);
        } else {
            System.err.println("VideoGamesSoundModel non initialisé dans le Controller.");
        }

        if (recordPlayer != null && recordPlayer.isRecording()) {
            long currentTime = System.currentTimeMillis();
            String noteToRecord = noteName + "4"; // Appending default octave 4

            recordPlayer.recordNoteOn(noteToRecord, currentTime);
            recordPlayer.recordNoteOff(noteToRecord, currentTime + 100);
        }
    }
}
package com.pianoo.controller;

import com.pianoo.model.*;
import com.pianoo.view.*;
import javax.swing.JOptionPane;

public class Controller implements IController, IOnChoiceSelectedListener, IMenuNavigationListener, ICatListener {

    // Déclarations des champs final (s'assurer qu'ils correspondent aux paramètres du constructeur)
    private final IMusicPlayer musicPlayer;
    private final IXylophonePlayer xylophonePlayer;
    private final IDrumsPlayer drumsPlayer;
    private final IOrganPlayer organPlayer;
    private final IRecordPlayer recordPlayer;
    private final IVideoGamesSoundModel videoGamesSoundModel;
    private final IKeyboardMapping keyboardMapping;
    private final ICatPlay catPlay;
    private final IMainMenu mainMenu; // mainMenu peut aussi être final s'il est fixé à la construction

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
                      ICatFrame catFrame, ICatPlay catPlay, IKeyboardMapping keyboardMapping) {
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
    }

    @Override
    public void onReturnMainMenu() {
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
        if (catPlay != null) {
            catPlay.stopSound();
        }
        if (recordPlayer != null && recordPlayer.isRecording()) {
            recordPlayer.stopRecording();
            updateAllRecordButtonsState(false);
        }
        showMainMenuScreen(); // Calls the private method
    }

    private void showMainMenuScreen() {
        if (mainMenu == null || mainMenu.getContentPane() == null) {
            System.err.println("MainMenu ou son content pane n'est pas initialisé.");
            return;
        }
        mainMenu.getContentPane().removeAll();
        mainMenu.initializeUI();
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
    }

    private void openPiano() {
        if (mainMenu == null || mainMenu.getContentPane() == null || pianoFrame == null || pianoFrame.getPanel() == null)
            return;
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
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(videoGamesFrame.getPanel());
        mainMenu.revalidate();
        mainMenu.repaint();
        videoGamesFrame.getPanel().requestFocusInWindow();
    }

    private void openOrgan() {
        if (mainMenu == null || mainMenu.getContentPane() == null || organFrame == null || organFrame.getPanel() == null)
            return;
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
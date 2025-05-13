package com.pianoo.controller;

import com.pianoo.model.ICatPlay;
import com.pianoo.model.IMusicPlayer;
import com.pianoo.model.IKeyboardMapping;
import com.pianoo.model.IXylophonePlayer;
import com.pianoo.model.IDrumsPlayer;
import com.pianoo.model.IOrganPlayer;
import com.pianoo.model.IRecordPlayer;

import com.pianoo.view.*;

import javax.swing.JOptionPane;

public class Controller implements IController, IOnChoiceSelectedListener, IMenuNavigationListener, ICatListener {

    private final IMusicPlayer musicPlayer;
    private final IXylophonePlayer xylophonePlayer;
    private final IDrumsPlayer drumsPlayer;
    private final IOrganPlayer organPlayer;
    private final IRecordPlayer recordPlayer;
    private IPianoFrame pianoFrame;
    private IOrganFrame organFrame;
    private IXylophoneFrame xylophoneFrame;
    private IVideoGamesFrame videoGamesFrame;
    private IDrumsFrame drumsFrame;
    private IRoundCloseButton roundCloseButton;
    private ICatFrame catFrame;
    private ICatPlay catPlay;
    private IPianoController pianoController;
    private IMainMenu mainMenu;
    private IKeyboardMapping keyboardMapping;

    public Controller(IMusicPlayer musicPlayer, IXylophonePlayer xylophonePlayer, IDrumsPlayer drumsPlayer, IOrganPlayer organPlayer, IRecordPlayer recordPlayer, IMainMenu mainMenu, IPianoFrame pianoFrame,
                      IOrganFrame organFrame, IXylophoneFrame xylophoneFrame, IVideoGamesFrame videoGamesFrame, IDrumsFrame drumsFrame, ICatFrame catFrame, ICatPlay catPlay,
                      IRoundCloseButton roundCloseButton, IKeyboardMapping keyboardMapping) {
        this.musicPlayer = musicPlayer;
        this.xylophonePlayer = xylophonePlayer;
        this.drumsPlayer = drumsPlayer;
        this.organPlayer = organPlayer;
        this.recordPlayer = recordPlayer;
        this.mainMenu = mainMenu;
        this.pianoFrame = pianoFrame;
        this.organFrame = organFrame;
        this.xylophoneFrame = xylophoneFrame;
        this.videoGamesFrame = videoGamesFrame;
        this.drumsFrame = drumsFrame;
        this.catFrame = catFrame;
        this.catPlay = catPlay;
        this.roundCloseButton = roundCloseButton;
        this.keyboardMapping = keyboardMapping;

        this.mainMenu.setInstrumentSelectedListener(this);
        this.mainMenu.setVisible(true);
        this.roundCloseButton.setListener(this);
        this.organFrame.setListener(this);
        this.pianoFrame.setListener(this);
        this.xylophoneFrame.setListener(this);
        this.videoGamesFrame.setListener(this);
        this.drumsFrame.setListener(this);
        this.catFrame.setListener(this);
        this.catFrame.setCatPlayListener(this);
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
        showMainMenuScreen();
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
        showMainMenuScreen();
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
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(pianoFrame.getPanel());
        pianoFrame.setKeyListener(this);
        mainMenu.revalidate();
        mainMenu.repaint();
        pianoFrame.getPanel().requestFocusInWindow();
    }

    private void openXylophone() {
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(xylophoneFrame.getPanel());
        xylophoneFrame.setKeyListener(this);
        mainMenu.revalidate();
        mainMenu.repaint();
        xylophoneFrame.getPanel().requestFocusInWindow();
    }

    private void openVideoGames() {
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(videoGamesFrame.getPanel());
        //videoGamesFrame.setKeyListener(this);
        mainMenu.revalidate();
        mainMenu.repaint();
        videoGamesFrame.getPanel().requestFocusInWindow();
    }

    private void openOrgan() {
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(organFrame.getPanel());
        organFrame.setController(this);
        mainMenu.revalidate();
        mainMenu.repaint();
        organFrame.getPanel().requestFocusInWindow();
    }

    private void openDrums() {
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(drumsFrame.getPanel());
        drumsFrame.setController(this);
        mainMenu.revalidate();
        mainMenu.repaint();
        drumsFrame.getPanel().requestFocusInWindow();
    }

    private void openCat() {
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(catFrame.getPanel());
        mainMenu.revalidate();
        mainMenu.repaint();
    }

    @Override
    public void setMainMenu(final IMainMenu mainMenu) {
        this.mainMenu = mainMenu;
        this.mainMenu.setInstrumentSelectedListener(this);
        this.mainMenu.setVisible(true);
    }

    @Override
    public void setPianoFrame(final IPianoFrame pianoFrame) {
        this.pianoFrame = pianoFrame;
        this.pianoFrame.setController(this);
    }

    @Override
    public void setXylophoneFrame(final IXylophoneFrame xylophoneFrame) {
        this.xylophoneFrame = xylophoneFrame;
        this.xylophoneFrame.setController(this);
    }

    @Override
    public void setKeyboardMapping(final IKeyboardMapping keyboardMapping) {
        this.keyboardMapping = keyboardMapping;
    }

    public void onDrumHit(String drumType) {
        drumsPlayer.playDrum(drumType);
    }

    @Override
    public void onKeyPressed(int noteValue, int octave) { // Piano
        int midiNote = musicPlayer.getMidiNote(octave, noteValue); // Vous l'avez déjà
        musicPlayer.playNote(midiNote);
        if (recordPlayer.isRecording()) {
            // Supposons que musicPlayer a maintenant getNoteName(noteValue, octave)
            String noteName = musicPlayer.getNoteName(noteValue, octave); // À AJOUTER à IMusicPlayer
            recordPlayer.recordNoteOn(noteName, System.currentTimeMillis());
        }
    }

    @Override
    public void onKeyReleased(int noteValue, int octave) { // Piano
        int midiNote = musicPlayer.getMidiNote(octave, noteValue);
        musicPlayer.stopNote(midiNote);
        if (recordPlayer.isRecording()) {
            String noteName = musicPlayer.getNoteName(noteValue, octave); // À AJOUTER à IMusicPlayer
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
        organPlayer.stopNote(midiNote);
        if (recordPlayer.isRecording()) {
            String noteName = organPlayer.getNoteNameFromMidi(midiNote); // À AJOUTER à IOrganPlayer
            recordPlayer.recordNoteOff(noteName, System.currentTimeMillis());
        }
    }

    @Override
    public void onNotePlayed(final String note) { // Xylophone
        System.out.println("Le xylophone joue la note : " + note);
        xylophonePlayer.playNote(note, xylophoneFrame.getNotes());
        if (recordPlayer.isRecording()) {
            long currentTime = System.currentTimeMillis();
            recordPlayer.recordNoteOn(note, currentTime);
            // Simuler une courte durée pour le xylophone, par exemple 100ms
            recordPlayer.recordNoteOff(note, currentTime + 100);
        }
    }

    @Override
    public int getMidiNoteFromKeyName(String noteName) {
        return organPlayer.getMidiNoteFromKeyName(noteName);
    }

    @Override
    public boolean isNoteActive(int midiNote) {
        return organPlayer.isNoteActive(midiNote);
    }

    @Override
    public int adjustMidiNoteForKeyboard(int baseMidiNote, boolean isUpperKeyboard) {
        return organPlayer.adjustMidiNoteForKeyboard(baseMidiNote, isUpperKeyboard);
    }

    @Override
    public int getMidiNoteForKeyCode(int keyCode) {
        return organPlayer.getMidiNoteForKeyCode(keyCode);
    }

    @Override
    public void onPlayCat() {
        catPlay.playMeowSound();
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public void toggleRecording() {
        if (recordPlayer.isRecording()) {
            recordPlayer.stopRecording();
        } else {
            String filename = JOptionPane.showInputDialog(null, "Entrez le nom du fichier pour l'enregistrement :", "Nom de l'enregistrement", JOptionPane.PLAIN_MESSAGE);
            if (filename != null && !filename.trim().isEmpty()) {
                recordPlayer.startRecording(filename);
            } else {
                System.out.println("Nom de fichier non valide ou annulé.");
                return;
            }
        }
        updateAllRecordButtonsState(recordPlayer.isRecording());
    }

}
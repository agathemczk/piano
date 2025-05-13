package com.pianoo.controller;

import com.pianoo.model.ICatPlay;
import com.pianoo.model.IMusicPlayer;
import com.pianoo.model.IKeyboardMapping;
import com.pianoo.model.IXylophonePlayer;
import com.pianoo.model.IDrumsPlayer;
import com.pianoo.model.IOrganPlayer;

import com.pianoo.view.*;

public class Controller implements IController, IOnChoiceSelectedListener, IMenuNavigationListener, ICatListener {

    private final IMusicPlayer musicPlayer;
    private final IXylophonePlayer xylophonePlayer;
    private final IDrumsPlayer drumsPlayer;
    private final IOrganPlayer organPlayer;
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

    public Controller(IMusicPlayer musicPlayer, IXylophonePlayer xylophonePlayer, IDrumsPlayer drumsPlayer, IOrganPlayer organPlayer, IMainMenu mainMenu, IPianoFrame pianoFrame,
                      IOrganFrame organFrame, IXylophoneFrame xylophoneFrame, IVideoGamesFrame videoGamesFrame, IDrumsFrame drumsFrame, ICatFrame catFrame, ICatPlay catPlay,
                      IRoundCloseButton roundCloseButton, IKeyboardMapping keyboardMapping) {
        this.musicPlayer = musicPlayer;
        this.mainMenu = mainMenu;
        this.pianoFrame = pianoFrame;
        this.organFrame = organFrame;
        this.organPlayer = organPlayer;
        this.xylophoneFrame = xylophoneFrame;
        this.xylophonePlayer = xylophonePlayer;
        this.videoGamesFrame = videoGamesFrame;
        this.drumsFrame = drumsFrame;
        this.drumsPlayer = drumsPlayer;
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
        catPlay.stopSound();
        openMainMenu();
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

    private void openMainMenu() {
        mainMenu.getContentPane().removeAll();
        mainMenu.initializeUI();
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

    @Override
    public void onNotePlayed(final String note) {
        System.out.println("Le xylophone joue la note : " + note);
        // Déléguer la conversion et la logique de jeu au modèle
        xylophonePlayer.playNote(note, xylophoneFrame.getNotes());
    }

    public void onDrumHit(String drumType) {
        // Appel au modèle pour jouer le son de batterie
        drumsPlayer.playDrum(drumType);
    }

    @Override
    public void onOrganKeyReleased(int midiNote) {
        organPlayer.stopNote(midiNote);
    }

    @Override
    public void onOrganKeyPressed(int midiNote) {
        organPlayer.playNote(midiNote, 100); // 100 est la vélocité par défaut
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
    public void onKeyPressed(int noteValue, int octave) {
        int midiNote = musicPlayer.getMidiNote(octave, noteValue);
        musicPlayer.playNote(midiNote);
    }

    @Override
    public void onKeyReleased(int noteValue, int octave) {
        int midiNote = musicPlayer.getMidiNote(octave, noteValue);
        musicPlayer.stopNote(midiNote);
    }
}
package com.pianoo.controller;

import com.pianoo.model.IMusicPlayer;
import com.pianoo.model.IKeyboardMapping;
import com.pianoo.model.IXylophonePlayer;
import com.pianoo.view.*;

public class Controller implements IController, IOnChoiceSelectedListener, IMenuNavigationListener {

    private final IMusicPlayer musicPlayer;
    private final IXylophonePlayer xylophonePlayer;
    private IPianoFrame pianoFrame;
    private IOrganFrame organFrame;
    private IXylophoneFrame xylophoneFrame;
    private IVideoGamesFrame videoGamesFrame;
    private IDrumsFrame drumsFrame;
    private IRoundCloseButton roundCloseButton;
    private IPianoController pianoController;
    private IMainMenu mainMenu;
    private IKeyboardMapping keyboardMapping;

    public Controller(IMusicPlayer musicPlayer, IXylophonePlayer xylophonePlayer, IMainMenu mainMenu, IPianoFrame pianoFrame,
                      IOrganFrame organFrame, IXylophoneFrame xylophoneFrame,
                      IVideoGamesFrame videoGamesFrame, IDrumsFrame drumsFrame,
                      IRoundCloseButton roundCloseButton, IKeyboardMapping keyboardMapping) {
        this.musicPlayer = musicPlayer;
        this.mainMenu = mainMenu;
        this.pianoFrame = pianoFrame;
        this.organFrame = organFrame;
        this.xylophoneFrame = xylophoneFrame;
        this.xylophonePlayer = xylophonePlayer;
        
        this.videoGamesFrame = videoGamesFrame;
        this.drumsFrame = drumsFrame;
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
    }

    @Override
    public void onReturnMainMenu() {
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
        //organFrame.setKeyListener(this);
        mainMenu.revalidate();
        mainMenu.repaint();
        organFrame.getPanel().requestFocusInWindow();
    }

    private void openDrums() {
        mainMenu.getContentPane().removeAll();
        mainMenu.getContentPane().add(drumsFrame.getPanel());
        //drumsFrame.setKeyListener(this);
        mainMenu.revalidate();
        mainMenu.repaint();
        drumsFrame.getPanel().requestFocusInWindow();
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

        // Convertir le nom de la note en indice
        int noteIndex = -1;
        String[] notes = xylophoneFrame.getNotes();
        for (int i = 0; i < notes.length; i++) {
            if (notes[i].equals(note)) {
                noteIndex = i;
                break;
            }
        }

        if (noteIndex >= 0) {
            // Utiliser l'octave 5 au lieu de 4 pour un son plus aigu
            int midiNote = xylophonePlayer.getMidiNote(5, noteIndex);
            xylophonePlayer.playNote(midiNote);
        }
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

    @Override
    public IView getView() {
        return null;
    }

    @Override
    public void setView(final IView view) {
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
package com.pianoo;

import com.pianoo.controller.*;
import com.pianoo.model.*;
import com.pianoo.view.*;

import java.awt.event.KeyListener;

public class Main {
    public static void main(String[] args) {
        IMusicPlayer musicPlayer = new MusicPlayer();
        IXylophonePlayer xylophonePlayer = new XylophonePlayer();
        IDrumsPlayer drumsPlayer = new DrumsPlayer();
        IOrganPlayer organPlayer = new OrganPlayer();
        IRecordPlayer recordPlayer = new RecordPlayer();
        MainMenu mainMenu = new MainMenu();
        IPianoFrame pianoFrame = new PianoFrame();
        IInstrumentFrame organFrame = new OrganFrame();
        IXylophoneFrame xylophoneFrame = new XylophoneFrame();
        IInstrumentFrame videoGamesFrame = new VideoGamesFrame();
        IVideoGamesSoundModel videoGamesPlayer = new VideoGamesSoundModel();
        IDrumsFrame drumsFrame = new DrumsFrame();
        IKeyboardMapping keyboardMapping = new KeyboardMapping(false);
        ICatFrame catFrame = new CatFrame();
        ICatPlay catPlay = new CatPlay();

        IScoreReader scoreReader = new ScoreReader();
        IScoreChooserView scoreChooserView = new ScoreChooserView(mainMenu);

        mainMenu.setVisible(true);


        IController controller = new Controller(musicPlayer, xylophonePlayer, drumsPlayer, organPlayer, recordPlayer, videoGamesPlayer, mainMenu, pianoFrame, organFrame, xylophoneFrame, videoGamesFrame, drumsFrame, catFrame, catPlay, keyboardMapping, scoreReader, scoreChooserView);
        KeyListener pianoController = new PianoController(pianoFrame, controller, keyboardMapping); //pour jouer avec le clavier


        controller.setPianoFrame(pianoFrame);
    }
}
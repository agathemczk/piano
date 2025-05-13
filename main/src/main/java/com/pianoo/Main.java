package com.pianoo;

import com.pianoo.controller.*;
import com.pianoo.model.*;
import com.pianoo.view.MainMenu;
import com.pianoo.view.IPianoFrame;
import com.pianoo.view.PianoFrame;
import com.pianoo.controller.Controller;
import com.pianoo.view.*;

public class Main {
    public static void main(String[] args) {
        IMusicPlayer musicPlayer = new MusicPlayer();
        IXylophonePlayer xylophonePlayer = new XylophonePlayer();
        IDrumsPlayer drumsPlayer = new DrumsPlayer();
        IOrganPlayer organPlayer = new OrganPlayer();
        MainMenu mainMenu = new MainMenu();
        IPianoFrame pianoFrame = new PianoFrame();
        IOrganFrame organFrame = new OrganFrame();
        IXylophoneFrame xylophoneFrame = new XylophoneFrame();
        IVideoGamesFrame videoGamesFrame = new VideoGamesFrame();
        IDrumsFrame drumsFrame = new DrumsFrame();
        IRoundCloseButton roundCloseButton = new RoundCloseButton();
        IKeyboardMapping keyboardMapping = new KeyboardMapping(false);
        ICatFrame catFrame = new CatFrame();
        ICatPlay catPlay = new CatPlay();
        // Dans la méthode main


        mainMenu.setVisible(true);


        IController controller = new Controller(musicPlayer, xylophonePlayer, drumsPlayer, organPlayer, mainMenu, pianoFrame, organFrame, xylophoneFrame, videoGamesFrame, drumsFrame, catFrame, catPlay, roundCloseButton, keyboardMapping);
        IPianoController pianoController = new PianoController(pianoFrame, controller, keyboardMapping); //pour jouer avec le clavier


        controller.setMainMenu(mainMenu);
        controller.setPianoFrame(pianoFrame);
        controller.setXylophoneFrame(xylophoneFrame);

        controller.start();
    }
}
package com.pianoo;

import com.pianoo.controller.*;
import com.pianoo.model.IKeyboardMapping;
import com.pianoo.model.KeyboardMapping;
import com.pianoo.view.MainMenu;
import com.pianoo.view.IPianoFrame;
import com.pianoo.view.PianoFrame;
import com.pianoo.controller.Controller;
import com.pianoo.model.IMusicPlayer;
import com.pianoo.model.MusicPlayer;
import com.pianoo.view.*;

public class Main {
    public static void main(String[] args) {
        IMusicPlayer musicPlayer = new MusicPlayer();
        MainMenu mainMenu = new MainMenu();
        IPianoFrame pianoFrame = new PianoFrame();
        IOrganFrame organFrame = new OrganFrame();
        IXylophoneFrame xylophoneFrame = new XylophoneFrame();
        IVideoGamesFrame videoGamesFrame = new VideoGamesFrame();
        IDrumsFrame drumsFrame = new DrumsFrame();
        IRoundCloseButton roundCloseButton = new RoundCloseButton();
        IKeyboardMapping keyboardMapping = new KeyboardMapping(false);

        mainMenu.setVisible(true);

        IController controller = new Controller(musicPlayer, mainMenu, pianoFrame, organFrame, xylophoneFrame, videoGamesFrame, drumsFrame, roundCloseButton, keyboardMapping);
        IPianoController pianoController = new PianoController(pianoFrame, controller, keyboardMapping);

        controller.setMainMenu(mainMenu);
        controller.setPianoFrame(pianoFrame);

        controller.start();
    }
}
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
        IXylophoneController xylophoneController = new XylophoneController(xylophoneFrame, controller, keyboardMapping, xylophonePlayer);

        controller.setMainMenu(mainMenu);
        controller.setPianoFrame(pianoFrame);
        controller.setXylophoneFrame(xylophoneFrame);

        controller.start();
    }
}
package com.pianoo;

import com.pianoo.controller.*;
import com.pianoo.view.MainMenu;
import com.pianoo.view.IPianoFrame;
import com.pianoo.view.PianoFrame;


public class Main {
    public static void main(String[] args) {
        IMusicPlayer musicPlayer = new MusicPlayer();
        MainMenu mainMenu = new MainMenu();
        IPianoFrame pianoFrame = new PianoFrame();

        mainMenu.setVisible(true);

        IController controller = new Controller(musicPlayer, mainMenu, pianoFrame);
        IPianoController pianoController = new PianoController(pianoFrame, controller);
        controller.start();
    }
}
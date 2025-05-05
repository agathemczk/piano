package com.pianoo;

import com.pianoo.controller.Controller;
import com.pianoo.controller.IMusicPlayer;
import com.pianoo.controller.MusicPlayer;
import com.pianoo.view.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        IMusicPlayer musicPlayer = new MusicPlayer();
        MainMenu mainMenu = new MainMenu();
        IPianoFrame pianoFrame = new PianoFrame();
        IOrganFrame organFrame = new OrganFrame();
        IXylophoneFrame xylophoneFrame = new XylophoneFrame();
        IVideoGamesFrame videoGamesFrame = new VideoGamesFrame();
        IRoundCloseButton roundCloseButton = new RoundCloseButton();


        mainMenu.setVisible(true); // Afficher le menu principal

        // Injecte toutes les dépendances dans le Controller
        Controller controller = new Controller(musicPlayer, mainMenu, pianoFrame, organFrame, xylophoneFrame, videoGamesFrame, roundCloseButton);
        controller.start();
    }
}
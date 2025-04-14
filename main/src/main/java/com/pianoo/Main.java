package com.pianoo;

import com.pianoo.controller.Controller;
import com.pianoo.controller.IMusicPlayer;
import com.pianoo.controller.MusicPlayer;
import com.pianoo.view.MainMenu;
import com.pianoo.view.IPianoFrame;
import com.pianoo.view.PianoFrame;
import com.pianoo.view.OrganFrame;
import com.pianoo.view.IOrganFrame;
import com.pianoo.view.IXylophoneFrame;
import com.pianoo.view.XylophoneFrame;
import com.pianoo.view.IVideoGamesFrame;
import com.pianoo.view.VideoGamesFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        IMusicPlayer musicPlayer = new MusicPlayer();
        MainMenu mainMenu = new MainMenu();
        IPianoFrame pianoFrame = new PianoFrame();
        IOrganFrame organFrame = new OrganFrame();
        IXylophoneFrame xylophoneFrame = new XylophoneFrame();
        IVideoGamesFrame videoGamesFrame = new VideoGamesFrame();


        mainMenu.setVisible(true); // Afficher le menu principal

        // Injecte toutes les dépendances dans le Controller
        Controller controller = new Controller(musicPlayer, mainMenu, pianoFrame, organFrame, xylophoneFrame, videoGamesFrame);
        controller.start();
    }
}
package com.pianoo;

import com.pianoo.controller.Controller;
import com.pianoo.controller.IMusicPlayer;
import com.pianoo.controller.MusicPlayer;
import com.pianoo.view.MainMenu;
import com.pianoo.view.IPianoFrame;
import com.pianoo.view.PianoFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        IMusicPlayer musicPlayer = new MusicPlayer();
        MainMenu mainMenu = new MainMenu();
        IPianoFrame pianoFrame = new PianoFrame(); // Crée une instance de PianoFrame

        mainMenu.setVisible(true); // Afficher le menu principal

        // Injecte toutes les dépendances dans le Controller
        Controller controller = new Controller(musicPlayer, mainMenu, pianoFrame);
        controller.start();
    }
}
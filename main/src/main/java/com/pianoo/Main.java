package com.pianoo;

import com.pianoo.controller.*;
import com.pianoo.model.*;
import com.pianoo.view.MainMenu;
import com.pianoo.view.IPianoFrame;
import com.pianoo.view.PianoFrame;
import com.pianoo.controller.Controller;
import com.pianoo.view.*;
import java.io.File;

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
        IScoreReader scoreReader = new ScoreReader();

        java.awt.Frame ownerFrame = null;
        if (mainMenu instanceof java.awt.Frame) {
            ownerFrame = (java.awt.Frame) mainMenu;
        } else {
            // Gérer le cas où mainMenu n'est pas une Frame.
            // Pourrait être un problème si vous comptez sur elle pour être le propriétaire.
            // Une solution temporaire pourrait être de créer un JFrame caché simple juste pour être propriétaire,
            // ou de passer null, mais avec les inconvénients mentionnés.

            System.err.println("Attention: MainMenu n'est pas une instance de java.awt.Frame. ScoreChooserView pourrait ne pas avoir de propriétaire correct.");
        }
        IScoreChooserView scoreChooserView = new ScoreChooserView(ownerFrame);
        mainMenu.setVisible(true);

        IController controller = new Controller(musicPlayer, xylophonePlayer, drumsPlayer, organPlayer, mainMenu, pianoFrame, organFrame, xylophoneFrame, videoGamesFrame, drumsFrame, catFrame, catPlay, roundCloseButton, keyboardMapping, scoreReader ,scoreChooserView);
        IPianoController pianoController = new PianoController(pianoFrame, controller, keyboardMapping); //pour jouer avec le clavier

        controller.setMainMenu(mainMenu);
        controller.setPianoFrame(pianoFrame);
        controller.setXylophoneFrame(xylophoneFrame);

        controller.start();
    }
}
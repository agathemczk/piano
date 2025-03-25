package com.pianoo;
import com.pianoo.controller.Controller;
import com.pianoo.controller.IMusicPlayer;
import com.pianoo.controller.MusicPlayer;
import com.pianoo.view.MainMenu;
import com.pianoo.view.IMainMenu;
import javax.swing.*;
import java.awt.event.*;


public class Main {
    public static void main(String[] args) {
        IMusicPlayer musicPlayer = new MusicPlayer();
        IMainMenu mainMenu = new MainMenu();
        Controller controller = new Controller(musicPlayer, mainMenu);
        controller.start();

        JFrame frame = new JFrame("Piano Simulator");
        JPanel panel = new JPanel();
        JButton[] keys = new JButton[13];

        for (int i = 1; i < 13; i++) {
            int key = i;
            keys[i] = new JButton("Key " + i);
            keys[i].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    controller.onKeyPressed(key, 4);
                }
                public void mouseReleased(MouseEvent e) {
                    controller.onKeyReleased(key, 4);
                }
            });
            panel.add(keys[i]);
        }

        frame.add(panel);
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


}
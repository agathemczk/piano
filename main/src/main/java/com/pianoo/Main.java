package com.pianoo;
import com.pianoo.controller.Controller;
import com.pianoo.controller.IMusicPlayer;
import com.pianoo.controller.MusicPlayer;
import javax.swing.*;
import java.awt.event.*;


public class Main {
    public static void main(String[] args) {
        IMusicPlayer musicPlayer = new MusicPlayer();
        Controller controller = new Controller(musicPlayer);
        controller.start();

        JFrame frame = new JFrame("Piano Simulator");
        JPanel panel = new JPanel();
        JButton[] keys = new JButton[12];

        for (int i = 0; i < 12; i++) {
            int key = i; // Key de 0 à 11 (do, do#, ré, etc.)
            keys[i] = new JButton("Key " + i);
            keys[i].addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    controller.onKeyPressed(key, 4); // Octave 4
                }
                public void mouseReleased(MouseEvent e) {
                    controller.onKeyReleased(key, 4); // Octave 4
                }
            });
            panel.add(keys[i]);
        }

        frame.add(panel);
        frame.setSize(400, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
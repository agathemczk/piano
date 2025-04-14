package com.pianoo.controller;

import com.pianoo.view.IPianoFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PianoController implements KeyListener {

    private final IPianoFrame view;

    public PianoController(IPianoFrame view) {
        this.view = view;
        this.view.addKeyListenerToFrame((KeyListener) this);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        char key = Character.toUpperCase(e.getKeyChar());

        switch (key) {
            case 'A':
                System.out.println("Do");
                break;
            case 'Z':
                System.out.println("Ré");
                break;
            case 'E':
                System.out.println("Mi");
                break;
            case 'R':
                System.out.println("Fa");
                break;
            case 'T':
                System.out.println("Sol");
                break;
            case 'Y':
                System.out.println("La");
                break;
            case 'U':
                System.out.println("Si");
                break;
            default:
                System.out.println("Touche non assignée : " + key);
        }
    }

    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
}

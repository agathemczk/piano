package com.pianoo.controller;

import com.pianoo.view.IPianoFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PianoController implements IPianoController, KeyListener {

    private final IPianoFrame view;
    private final IController controller;

    public PianoController(IPianoFrame view, IController controller) {
        this.view = view;
        this.controller = controller;
        this.view.addKeyListenerToFrame(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int note = mapKeyToNote(e.getKeyChar());
        int octave = 5; // À rendre dynamique via la vue plus tard

        if (note != -1) {
            controller.onKeyPressed(note, octave);
        } else {
            System.out.println("Touche non assignée : " + e.getKeyChar());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int note = mapKeyToNote(e.getKeyChar());
        int octave = 5;

        if (note != -1) {
            controller.onKeyReleased(note, octave);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // non utilisé
    }

    private int mapKeyToNote(char keyChar) {
        return switch (Character.toUpperCase(keyChar)) {
            case 'A' -> 0;  // Do
            case 'Z' -> 2;  // Ré
            case 'E' -> 4;  // Mi
            case 'R' -> 5;  // Fa
            case 'T' -> 7;  // Sol
            case 'Y' -> 9;  // La
            case 'U' -> 11; // Si
            default -> -1;
        };
    }
}

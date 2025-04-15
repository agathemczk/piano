package com.pianoo.controller;

import com.pianoo.view.IPianoFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class PianoController implements IPianoController, KeyListener {

    private final IPianoFrame view;
    private final IController controller;
    private final Set<Character> keysPressed;

    public PianoController(IPianoFrame view, IController controller, final Set<Character> keysPressed) {
        this.view = view;
        this.controller = controller;
        this.keysPressed = new HashSet<>();
        this.view.addKeyListenerToFrame(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = Character.toUpperCase(e.getKeyChar());

        if (keysPressed.contains(key)) return; // touche déjà enfoncée → on ne rejoue pas la note

        keysPressed.add(key); // on marque la touche comme enfoncée

        int note = -1;
        int octave = view.getSelectedOctave();

        switch (key) {
            case 'A': note = 0; break; // Do
            case 'Z': note = 2; break; // Ré
            case 'E': note = 4; break; // Mi
            case 'R': note = 5; break; // Fa
            case 'T': note = 7; break; // Sol
            case 'Y': note = 9; break; // La
            case 'U': note = 11; break; // Si
        }

        if (note != -1) {
            controller.onKeyPressed(note, octave);
        } else {
            System.out.println("Touche non assignée : " + key);
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        char key = Character.toUpperCase(e.getKeyChar());
        keysPressed.remove(key);

        int note = -1;
        int octave = view.getSelectedOctave();

        switch (key) {
            case 'A': note = 0; break;
            case 'Z': note = 2; break;
            case 'E': note = 4; break;
            case 'R': note = 5; break;
            case 'T': note = 7; break;
            case 'Y': note = 9; break;
            case 'U': note = 11; break;
        }

        if (note != -1) {
            controller.onKeyReleased(note, octave);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // non utilisé
    }
}

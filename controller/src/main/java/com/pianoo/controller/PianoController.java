package com.pianoo.controller;

import com.pianoo.view.IPianoFrame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PianoController implements IPianoController, KeyListener {

    private final IPianoFrame view;
    private final IController controller;
    private final Set<Character> keysPressed;
    private final Map<Character, Integer> keyToNoteMap;

    public PianoController(IPianoFrame view, IController controller, final Set<Character> keysPressed) {
        this.view = view;
        this.controller = controller;
        this.keysPressed = new HashSet<>();
        this.view.addKeyListenerToFrame(this);

        this.keyToNoteMap = new HashMap<>();
        keyToNoteMap.put('A', 0);
        keyToNoteMap.put('W', 1);
        keyToNoteMap.put('Z', 2);
        keyToNoteMap.put('X', 3);
        keyToNoteMap.put('E', 4);
        keyToNoteMap.put('R', 5);
        keyToNoteMap.put('C', 6);
        keyToNoteMap.put('T', 7);
        keyToNoteMap.put('V', 8);
        keyToNoteMap.put('Y', 9);
        keyToNoteMap.put('B', 10);
        keyToNoteMap.put('U', 11);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = Character.toUpperCase(e.getKeyChar());
        if (keysPressed.contains(key)) return;

        Integer note = keyToNoteMap.get(key);
        if (note != null) {
            int octave = view.getSelectedOctave();
            keysPressed.add(key);

            controller.onKeyPressed(note, octave);
            view.highlightKey(note, octave);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char key = Character.toUpperCase(e.getKeyChar());
        keysPressed.remove(key);

        Integer note = keyToNoteMap.get(key);
        if (note != null) {
            int octave = view.getSelectedOctave();

            controller.onKeyReleased(note, octave);
            view.resetKey(note, octave);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }
}
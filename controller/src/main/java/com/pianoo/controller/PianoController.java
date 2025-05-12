package com.pianoo.controller;

import com.pianoo.model.IKeyboardMapping;
import com.pianoo.view.IPianoFrame;

import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

public class PianoController implements IPianoController {

    private final IPianoFrame view;
    private final IController controller;
    private final Set<Character> keysPressed;
    private IKeyboardMapping keyboardMapping;

    public PianoController(IPianoFrame view, IController controller,  IKeyboardMapping keyboardMapping) {
        this.view = view;
        this.controller = controller;
        this.keysPressed = new HashSet<>();
        this.keyboardMapping = keyboardMapping;
        this.view.addKeyListenerToFrame(this);
    }

    @Override
    public void setKeyboardMapping(IKeyboardMapping keyboardMapping) {
        this.keyboardMapping = keyboardMapping;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        if (keysPressed.contains(key)) return;

        Integer noteValue = keyboardMapping.getNoteFromKey(key);
        if (noteValue != null) {
            int octave = view.getSelectedOctave();
            keysPressed.add(key);

            controller.onKeyPressed(noteValue, octave);
            view.highlightKey(noteValue, octave);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char key = e.getKeyChar();
        keysPressed.remove(key);

        Integer noteValue = keyboardMapping.getNoteFromKey(key);
        if (noteValue != null) {
            int octave = view.getSelectedOctave();

            controller.onKeyReleased(noteValue, octave);
            view.resetKey(noteValue, octave);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
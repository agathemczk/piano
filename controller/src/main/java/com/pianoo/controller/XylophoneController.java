package com.pianoo.controller;

import com.pianoo.model.IKeyboardMapping;
import com.pianoo.view.IXylophoneFrame;
import com.pianoo.model.IXylophonePlayer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class XylophoneController implements IXylophoneController, KeyListener {

    private final IXylophoneFrame view;
    private final IController controller;
    private IKeyboardMapping keyboardMapping;
    private final Set<Character> keysPressed;
    private final IXylophonePlayer xylophonePlayer;

    public XylophoneController(IXylophoneFrame view,
                               IController controller,
                               IKeyboardMapping keyboardMapping,
                               IXylophonePlayer xylophonePlayer) {
        this.view = view;
        this.controller = controller;
        this.keyboardMapping = keyboardMapping;
        this.xylophonePlayer = xylophonePlayer;
        this.keysPressed = new HashSet<>();
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
            keysPressed.add(key);
            controller.onKeyPressed(noteValue, 0);
            view.highlightNote(noteValue);
            int midiNote = xylophonePlayer.getMidiNote(5, noteValue);
            xylophonePlayer.playNote(midiNote);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        char key = e.getKeyChar();
        keysPressed.remove(key);

        Integer noteValue = keyboardMapping.getNoteFromKey(key);
        if (noteValue != null) {
            controller.onKeyReleased(noteValue, 0);
            view.resetNote(noteValue);
            int midiNote = xylophonePlayer.getMidiNote(5, noteValue);
            xylophonePlayer.stopNote(midiNote);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used
    }

}

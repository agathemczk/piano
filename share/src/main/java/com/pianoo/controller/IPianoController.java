package com.pianoo.controller;

import java.awt.event.KeyEvent;

public interface IPianoController {
    void keyPressed(KeyEvent e);
    void keyReleased(KeyEvent e);
    void keyTyped(KeyEvent e);
}

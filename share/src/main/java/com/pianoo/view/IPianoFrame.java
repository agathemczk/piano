package com.pianoo.view;

import com.pianoo.controller.IController;

import javax.swing.*;
import java.awt.event.KeyListener;

public interface IPianoFrame {

    JPanel getPanel();

    void setListener(IMenuNavigationListener listener);

    void addKeyListenerToFrame(KeyListener listener);

    void setKeyListener(IController controller);

    void setController(IController controller);

    int getSelectedOctave();

    void highlightKey(int note, int octave);

    void resetKey(int note, int octave);
}
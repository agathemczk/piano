package com.pianoo.view;

import javax.swing.*;
import java.awt.event.KeyListener;

public interface IPianoFrame {
    JPanel getPanel();

    void addKeyListenerToFrame(KeyListener listener);

    void setKeyListener(com.pianoo.controller.IController controller);

    int getSelectedOctave();
}

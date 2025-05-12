package com.pianoo.view;

import com.pianoo.controller.IController;

import javax.swing.*;
import java.awt.event.KeyListener;

public interface IXylophoneFrame {

    JPanel getPanel();

    void setListener(IMenuNavigationListener listener);

    void addKeyListenerToFrame(KeyListener listener);

    void setKeyListener(IController controller);

    void setController(IController controller);

    void highlightNote(int note);

    void resetNote(int note);

}

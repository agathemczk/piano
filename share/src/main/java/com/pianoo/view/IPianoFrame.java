package com.pianoo.view;

import javax.swing.*;
import java.awt.event.KeyListener;

public interface IPianoFrame {
    JPanel getPanel();
    void setListener(IMenuNavigationListener listener);
    void addKeyListenerToFrame(KeyListener listener);
    void startPiano(); // Par exemple, pour lancer la vue du piano
    void stopPiano();  // Par exemple, pour arrêter la vue du piano
}

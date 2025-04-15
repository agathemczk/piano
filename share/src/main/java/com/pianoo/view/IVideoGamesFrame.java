package com.pianoo.view;

import javax.swing.*;

public interface IVideoGamesFrame {
    JPanel getPanel();

    void setQuitButtonSelectedListener(IQuitButtonSelectedListener listener);
}
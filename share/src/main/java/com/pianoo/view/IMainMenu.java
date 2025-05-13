package com.pianoo.view;

import javax.swing.*;

public interface IMainMenu {
    void setVisible(boolean visible);
    void setInstrumentSelectedListener(IOnChoiceSelectedListener listener);
    JPanel getContentPane();
    void revalidate();
    void repaint();

    void removeAll();

    void add(JPanel panel);

    void initializeUI();
}
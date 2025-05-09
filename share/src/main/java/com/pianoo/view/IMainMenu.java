package com.pianoo.view;

import javax.swing.*;

public interface IMainMenu {
    void setVisible(boolean visible);
    void setInstrumentSelectedListener(IOnChoiceSelectedListener listener);
    JPanel getContentPane(); // Ajoutez cette méthode si elle manque
    void revalidate();       // Ajoutez cette méthode si elle manque
    void repaint();

    JPanel getMainPanel();

    void removeAll();

    void add(JPanel panel);

    void initializeUI();
}

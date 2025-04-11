package com.pianoo.view;

import javax.swing.*;
import java.util.function.Consumer;

public interface IMainMenu {
    void setVisible(boolean visible);
    void setInstrumentSelectedListener(IOnInstrumentSelectedListener listener);
    JPanel getContentPane(); // Ajoutez cette méthode si elle manque
    void revalidate();       // Ajoutez cette méthode si elle manque
    void repaint();

}

package com.pianoo.view;

import com.pianoo.controller.IController; // Ajout pour setController
import javax.swing.JPanel;

public interface IInstrumentFrame {
    JPanel getPanel();
    void setListener(IMenuNavigationListener listener);
    void setController(IController controller);
    void updateRecordButtonState(boolean isRecording);
    void setKeyListener(final IController controller);
}

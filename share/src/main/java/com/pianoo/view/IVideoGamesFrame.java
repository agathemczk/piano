package com.pianoo.view;

import com.pianoo.controller.IController;
import javax.swing.*;

public interface IVideoGamesFrame {

    JPanel getPanel();

    void setListener(IMenuNavigationListener listener);

    void setController(IController controller);

    void updateRecordButtonState(boolean isRecording);

}
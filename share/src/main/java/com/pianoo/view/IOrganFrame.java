package com.pianoo.view;

import com.pianoo.controller.IController;

import javax.swing.*;

public interface IOrganFrame {
    JPanel getPanel();
    void setListener(IMenuNavigationListener listener);
    void setController(IController controller);
    void setKeyListener(IController controller);
}
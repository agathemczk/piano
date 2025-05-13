package com.pianoo.view;

import java.awt.Component;
import com.pianoo.controller.IController;

import javax.swing.*;

public interface IOrganFrame {
    JPanel getPanel();
    void setListener(IMenuNavigationListener listener);
    void setController(IController controller);
    void setKeyListener(IController controller);
}
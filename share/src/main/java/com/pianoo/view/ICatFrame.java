package com.pianoo.view;

import com.pianoo.controller.IController;

import javax.swing.*;
import java.awt.*;

public interface ICatFrame {

    void setListener(IMenuNavigationListener listener);

    void setCatPlayListener(ICatListener listener);

    JPanel getPanel();
}

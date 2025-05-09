package com.pianoo.view;

import javax.swing.*;

public interface IXylophoneFrame {
    JPanel getPanel();

    void setListener(IMenuNavigationListener listener);
}

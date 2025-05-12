package com.pianoo.view;

import javax.swing.*;

public interface IOrganFrame {

    JPanel getPanel();

    void setListener(IMenuNavigationListener listener);
}

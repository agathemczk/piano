package com.pianoo.view;

import javax.swing.*;

public interface ICatFrame {
    void setListener(IMenuNavigationListener listener);
    void setCatPlayListener(ICatListener listener);
    JPanel getPanel();
}

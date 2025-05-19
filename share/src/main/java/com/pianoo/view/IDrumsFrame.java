package com.pianoo.view;

import com.pianoo.controller.IController;

public interface IDrumsFrame extends IInstrumentFrame {
    void setListener(IMenuNavigationListener listener);
    void setController(IController controller);
}
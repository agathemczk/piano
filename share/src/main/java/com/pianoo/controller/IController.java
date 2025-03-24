package com.pianoo.controller;
import com.pianoo.model.IModel;
import com.pianoo.view.IView;

public interface IController {
    void start();
    void stop();
    IView getView();
    void setView(IView view);
    IModel getModel();
    void setModel(IModel model);
    void onKeyPressed(int key, int octave);
    void onKeyReleased(int key, int octave);
}

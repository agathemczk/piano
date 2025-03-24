package com.pianoo.view;
import com.pianoo.view.IView;

public class View implements IView {

    @Override
    public void displayMessage(String message) {
        System.out.println(message);
    }
}

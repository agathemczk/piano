package com.pianoo.controller;
import com.pianoo.model.IKeyboardMapping;

import java.awt.event.KeyListener;

public interface IPianoController extends KeyListener {
    void setKeyboardMapping(IKeyboardMapping keyboardMapping);
}
package com.pianoo.model;

public interface IKeyboardMapping {
    Integer getNoteFromKey(char key);
    Integer getNoteFromKeyCode(int keyCode);
    boolean isAzertyLayout();
}

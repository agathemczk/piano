package com.pianoo.model;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class KeyboardMapping implements IKeyboardMapping {
    private final Map<Character, Integer> keyToNoteMap;
    private final boolean isAzerty;

    public static KeyboardMapping createAzertyMapping() {
        return new KeyboardMapping(true);
    }

    public static KeyboardMapping createQwertyMapping() {
        return new KeyboardMapping(false);
    }

    public KeyboardMapping(boolean isAzerty) {
        this.isAzerty = isAzerty;
        this.keyToNoteMap = new HashMap<>();

        if (isAzerty) {
            keyToNoteMap.put('A', 0);
            keyToNoteMap.put('Z', 1);
            keyToNoteMap.put('E', 2);
            keyToNoteMap.put('R', 3);
            keyToNoteMap.put('T', 4);
            keyToNoteMap.put('Y', 5);
            keyToNoteMap.put('U', 6);
        } else {
            keyToNoteMap.put('A', 0);
            keyToNoteMap.put('W', 1);
            keyToNoteMap.put('S', 2);
            keyToNoteMap.put('E', 3);
            keyToNoteMap.put('D', 4);
            keyToNoteMap.put('F', 5);
            keyToNoteMap.put('T', 6);
            keyToNoteMap.put('G', 7);
            keyToNoteMap.put('Y', 8);
            keyToNoteMap.put('H', 9);
            keyToNoteMap.put('U', 10);
            keyToNoteMap.put('J', 11);
        }
    }

    @Override
    public Integer getNoteFromKey(char key) {
        return keyToNoteMap.get(Character.toUpperCase(key));
    }

    @Override
    public Integer getNoteFromKeyCode(int keyCode) {
        if (isAzerty) {
            switch (keyCode) {
                case KeyEvent.VK_A: return 0;
                case KeyEvent.VK_Z: return 1;
                case KeyEvent.VK_E: return 2;
                case KeyEvent.VK_R: return 3;
                case KeyEvent.VK_T: return 4;
                case KeyEvent.VK_Y: return 5;
                case KeyEvent.VK_U: return 6;
                default: return null;
            }
        } else {
            switch (keyCode) {
                case KeyEvent.VK_A: return 0;
                case KeyEvent.VK_W: return 1;
                case KeyEvent.VK_S: return 2;
                case KeyEvent.VK_E: return 3;
                case KeyEvent.VK_D: return 4;
                case KeyEvent.VK_F: return 5;
                case KeyEvent.VK_T: return 6;
                case KeyEvent.VK_G: return 7;
                case KeyEvent.VK_Y: return 8;
                case KeyEvent.VK_H: return 9;
                case KeyEvent.VK_U: return 10;
                case KeyEvent.VK_J: return 11;
                default: return null;
            }
        }
    }

    @Override
    public boolean isAzertyLayout() {
        return isAzerty;
    }
}
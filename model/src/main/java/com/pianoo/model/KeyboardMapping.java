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
            return switch (keyCode) {
                case KeyEvent.VK_A -> 0;
                case KeyEvent.VK_Z -> 1;
                case KeyEvent.VK_E -> 2;
                case KeyEvent.VK_R -> 3;
                case KeyEvent.VK_T -> 4;
                case KeyEvent.VK_Y -> 5;
                case KeyEvent.VK_U -> 6;
                default -> null;
            };
        } else {
            return switch (keyCode) {
                case KeyEvent.VK_A -> 0;
                case KeyEvent.VK_W -> 1;
                case KeyEvent.VK_S -> 2;
                case KeyEvent.VK_E -> 3;
                case KeyEvent.VK_D -> 4;
                case KeyEvent.VK_F -> 5;
                case KeyEvent.VK_T -> 6;
                case KeyEvent.VK_G -> 7;
                case KeyEvent.VK_Y -> 8;
                case KeyEvent.VK_H -> 9;
                case KeyEvent.VK_U -> 10;
                case KeyEvent.VK_J -> 11;
                default -> null;
            };
        }
    }

    @Override
    public boolean isAzertyLayout() {
        return isAzerty;
    }
}
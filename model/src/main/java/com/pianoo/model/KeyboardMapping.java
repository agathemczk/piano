package com.pianoo.model;

import java.util.HashMap;
import java.util.Map;

public class KeyboardMapping implements IKeyboardMapping {
    private final Map<Character, Integer> keyToNoteMap;

    public KeyboardMapping(boolean isAzerty) {
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
}
package com.pianoo.view;

import java.awt.event.KeyListener;

public interface IPianoFrame extends IInstrumentFrame {
    void addKeyListenerToFrame(KeyListener listener);
    int getSelectedOctave();
    void highlightKey(int note, int octave);
    void resetKey(int note, int octave);
}
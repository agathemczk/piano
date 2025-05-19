package com.pianoo.view;

public interface IXylophoneFrame extends IInstrumentFrame{
    void highlightNote(int note);
    void resetNote(int note);
    String[] getNotes();
}
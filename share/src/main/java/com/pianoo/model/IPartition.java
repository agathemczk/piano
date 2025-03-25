package com.pianoo.model;

import java.util.ArrayList;
import java.util.List;

public interface IPartition {
    public String fileName = "";
    public List<INote> notes = new ArrayList<INote>();

    void addNote(INote note);
    void displayPartition ();
    void playPartition();
}

package com.pianoo.model;

import com.pianoo.model.IInstrument;
import java.util.ArrayList;
import java.util.List;

public interface IRecord {
    String filename = "";
    List<INote> notes = new ArrayList<INote>();

    void addNote(IInstrument instrument, INote note);
    void emptyIngRecord();
    void close();

}

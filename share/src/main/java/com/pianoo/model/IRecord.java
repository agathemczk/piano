package com.pianoo.model;

import java.util.ArrayList;
import java.util.List;

public interface IRecord {
    String filename = "";
    List<INote> notes = new ArrayList<INote>();

    void emptyIngRecord();
    void close();

}

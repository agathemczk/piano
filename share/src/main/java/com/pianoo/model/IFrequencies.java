package com.pianoo.model;

import java.util.HashMap;
import java.util.Map;

public interface IFrequencies {

    Map<String, Integer> frequenciesNotes=new HashMap();

    int getMide(INote note);
}

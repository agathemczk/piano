package com.pianoo.model;

import java.util.HashMap;
import java.util.Map;

public interface IFrequencies {

    Map<String, Integer> frequenciesNotes=new HashMap(); //pour les octaves ?? vraiment ?

    int getMide(INote note);
}

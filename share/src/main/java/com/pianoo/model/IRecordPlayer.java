package com.pianoo.model;

public interface IRecordPlayer {
    boolean startRecording(String filename);
    void stopRecording();

    void recordNoteOn(String noteName, long timestamp);

    void recordNoteOff(String noteName, long timestamp);

    boolean isRecording();
}
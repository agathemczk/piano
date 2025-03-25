package com.pianoo.model;

public interface ISound {
    IPartition partition = null;

    void playMusic();

    void stopMusic();
}

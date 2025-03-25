package com.pianoo.controller;

import com.pianoo.model.IPartition;

public class PartitionLoader implements IPartitionLoader {

    @Override
    public IPartition loadPartition(final String filepath) {
        System.out.println("Loading partition from " + filepath);
        return null;
    }
}

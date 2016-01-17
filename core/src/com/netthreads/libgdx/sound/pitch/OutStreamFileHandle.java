package com.netthreads.libgdx.sound.pitch;

import com.badlogic.gdx.files.FileHandle;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * This is a hi-jacked FileHandle class that lets us insert out own data and file type in a consuming client.
 */
public class OutStreamFileHandle extends FileHandle {
    private String type;
    private byte[] data;

    public OutStreamFileHandle(String type, byte[] data) {
        this.type = type;
        this.data = data;
    }

    @Override
    public String extension() {
        return type;
    }

    @Override
    public InputStream read() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);

        return byteArrayInputStream;
    }

    @Override
    public String toString() {
        return OutStreamFileHandle.class.getCanonicalName();
    }
}
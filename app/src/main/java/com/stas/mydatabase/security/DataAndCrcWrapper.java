package com.stas.mydatabase.security;

import androidx.annotation.NonNull;

/**
 * Wrapper over crc and data byte arrays
 */
public class DataAndCrcWrapper {
    @NonNull
    private byte[] data;
    @NonNull
    private byte[] crc;

    DataAndCrcWrapper(@NonNull byte[] data, @NonNull byte[] crc) {
        this.data = data;
        this.crc = crc;
    }

    public byte[] getData() {
        return data;
    }

    byte[] getCrc() {
        return crc;
    }
}

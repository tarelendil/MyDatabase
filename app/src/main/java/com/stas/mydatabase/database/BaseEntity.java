package com.stas.mydatabase.database;


public abstract class BaseEntity {
    public BaseEntity(byte[] crc) {
        this.crc = crc;
    }

    public byte[] crc;
}

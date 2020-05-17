package com.stas.mydatabase.security;


import com.stas.mydatabase.security.exceptions.DataTypeForEncryptionNotSupportedException;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import kotlin.text.Charsets;

public class ByteUtils {
    public static byte[] toBytes(boolean value) {
        return new byte[]{(byte) (value ? 1 : 0)};
    }

    public static byte[] toBytes(long value) {
        return ByteBuffer.allocate(Long.BYTES).putLong(value).array();
    }

    public static byte[] toBytes(int value) {
        return ByteBuffer.allocate(Integer.BYTES).putInt(value).array();
    }

    public static byte[] toBytes(float value) {
        return ByteBuffer.allocate(Float.BYTES).putFloat(value).array();
    }

    public static byte[] toBytes(String value) {
        return value.getBytes(Charsets.UTF_8);
    }

    public static Byte toByte(boolean value) {
        return (byte) (value ? 1 : 0);
    }

    public static byte[] toBytes(Object... values) throws DataTypeForEncryptionNotSupportedException {
        ArrayList<Byte> resultBytes = new ArrayList<>();
        for (Object value : values) {
            if (value instanceof String) {
                resultBytes.addAll(toList(toBytes((String) value)));
            } else if (value instanceof Integer) {
                resultBytes.addAll(toList(toBytes((Integer) value)));
            } else if (value instanceof Long) {
                resultBytes.addAll(toList(toBytes((Long) value)));
            } else if (value instanceof Float) {
                resultBytes.addAll(toList(toBytes((Float) value)));
            } else if (value instanceof Boolean) {
                resultBytes.add(toByte((Boolean) value));
            } else {
                throw new DataTypeForEncryptionNotSupportedException(value.getClass().getName());
            }
        }
        return toBytes(resultBytes);
//        return resultBytes.toArray(new Byte[0]);
    }

    public static List<Byte> toList(byte[] bytes) {
        List<Byte> list = new ArrayList<>(bytes.length);
        for (byte b : bytes) list.add(b);
        return list;
    }

    public static byte[] toBytes(List<Byte> list) {
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }
        return bytes;
    }
}

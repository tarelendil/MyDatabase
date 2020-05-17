package com.stas.mydatabase.security;

import java.util.zip.CRC32;

/**
 * Class for Crc operations, just a wrapper around Java CRC32 implementation
 */
public class Crc {

    private CRC32 crc32 = new CRC32();

    /**
     * @param bytes on which to calculated the crc on
     * @return calculated crc value
     */
    public long getCrcValue(byte[] bytes) {
        crc32.reset();
        crc32.update(bytes);
        return crc32.getValue();
    }

}

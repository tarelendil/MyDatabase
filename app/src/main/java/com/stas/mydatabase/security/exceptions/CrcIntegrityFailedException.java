package com.stas.mydatabase.security.exceptions;

import java.security.GeneralSecurityException;

public class CrcIntegrityFailedException extends GeneralSecurityException {
    public CrcIntegrityFailedException() {
        super("CRC comparision Failed");
    }

}

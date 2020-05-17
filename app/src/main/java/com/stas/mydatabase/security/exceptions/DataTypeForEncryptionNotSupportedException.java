package com.stas.mydatabase.security.exceptions;

import java.security.GeneralSecurityException;

public class DataTypeForEncryptionNotSupportedException extends GeneralSecurityException {
    public DataTypeForEncryptionNotSupportedException() {
        super("Type not found");
    }

    public DataTypeForEncryptionNotSupportedException(String msg) {
        super("class " + msg + " not supported");
    }
}

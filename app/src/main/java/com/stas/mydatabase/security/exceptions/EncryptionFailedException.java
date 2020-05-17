package com.stas.mydatabase.security.exceptions;

import java.security.GeneralSecurityException;

public class EncryptionFailedException extends GeneralSecurityException {
    EncryptionFailedException() {
        super("Encryption Failed");
    }

     public EncryptionFailedException(String msg) {
        super(msg);
    }
}

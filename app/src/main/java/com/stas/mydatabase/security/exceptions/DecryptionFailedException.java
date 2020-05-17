package com.stas.mydatabase.security.exceptions;

import java.security.GeneralSecurityException;

public class DecryptionFailedException extends GeneralSecurityException {
    DecryptionFailedException() {
        super("Decryption Failed");
    }

     public DecryptionFailedException(String msg) {
        super(msg);
    }
}

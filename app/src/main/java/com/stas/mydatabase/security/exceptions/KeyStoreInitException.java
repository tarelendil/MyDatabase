package com.stas.mydatabase.security.exceptions;

public class KeyStoreInitException extends RuntimeException {
    KeyStoreInitException() {
        super("KeyStore Init Failed");
    }

     public KeyStoreInitException(String msg) {
        super(msg);
    }
}

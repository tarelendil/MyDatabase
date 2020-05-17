package com.stas.mydatabase.security;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import com.stas.mydatabase.security.exceptions.KeyStoreInitException;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;


/**
 * this class will work with android 6 and above, as we don't support android bellow 7 either way
 */
public class KeyStoreSecurity {
    private final String AndroidKeyStore = "AndroidKeyStore";
    private final String AES_MODE = "AES/GCM/NoPadding";
    private final String KEY_ALIAS = "TJTripleJump";
    private static final byte[] FIXED_IV = new byte[]{55, 54, 53, 52, 51, 50,
            49, 48, 47,
            46, 45, 44};

    /**
     * Creates the keys, checks if keys were already created
     * Keys must be created before using encryption nd decryption methods
     */
    public void init() {
        try {
            KeyStore keyStore = KeyStore.getInstance(AndroidKeyStore);
            keyStore.load(null);
            if (!keyStore.containsAlias(KEY_ALIAS)) {
                KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, AndroidKeyStore);
                keyGenerator.init(
                        new KeyGenParameterSpec.Builder(KEY_ALIAS,
                                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                                .setBlockModes(KeyProperties.BLOCK_MODE_GCM).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                                .setRandomizedEncryptionRequired(false)
                                .build());
                keyGenerator.generateKey();
            }
        } catch (IOException | CertificateException | KeyStoreException | NoSuchProviderException | InvalidAlgorithmParameterException | NoSuchAlgorithmException e) {
            throw new KeyStoreInitException(e.getMessage());
        }
    }

    /**
     * @param originalData byte array of data to encrypt
     * @return encrypted data byte array
     */
    public byte[] encryptDataBytes(byte[] originalData) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, InvalidAlgorithmParameterException, InvalidKeyException {
        Cipher c;
        c = Cipher.getInstance(AES_MODE);
        c.init(Cipher.ENCRYPT_MODE, getSecretKey(), new GCMParameterSpec(128, FIXED_IV));
        return c.doFinal(originalData);
    }

    /**
     * @param encryptedData byte array of encrypted data to decrypt
     * @return decrypted data byte array
     */
    public byte[] decryptDataBytes(byte[] encryptedData) throws NoSuchPaddingException, NoSuchAlgorithmException, UnrecoverableKeyException, CertificateException, KeyStoreException, IOException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher c = Cipher.getInstance(AES_MODE);
        c.init(Cipher.DECRYPT_MODE, getSecretKey(), new GCMParameterSpec(128, FIXED_IV));
        return c.doFinal(encryptedData);
    }

    /**
     * @return the ket corresponding to the given key alias
     */
    private Key getSecretKey() throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, UnrecoverableKeyException {
        KeyStore keyStore = KeyStore.getInstance(AndroidKeyStore);
        keyStore.load(null);
        return keyStore.getKey(KEY_ALIAS, null);
    }
}

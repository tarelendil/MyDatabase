package com.stas.mydatabase.security;

import com.stas.mydatabase.security.DataAndCrcWrapper;
import com.stas.mydatabase.security.exceptions.CrcIntegrityFailedException;
import com.stas.mydatabase.security.exceptions.DataTypeForEncryptionNotSupportedException;
import com.stas.mydatabase.security.exceptions.DecryptionFailedException;
import com.stas.mydatabase.security.exceptions.EncryptionFailedException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import kotlin.text.Charsets;

import static com.stas.mydatabase.security.ByteUtils.toBytes;

public class DataSecurity {

    private static DataSecurity instance = new DataSecurity();
    private Crc crcIntegrity = new Crc();
    private KeyStoreSecurity keyStoreSecurity = new KeyStoreSecurity();

    /**
     * initializes key store keys
     */
    private DataSecurity() {
        keyStoreSecurity.init();
    }

    /**
     * @return singleton instance
     */
    public static DataSecurity getInstance() {
        return instance;
    }

    /**
     * @param bytes to encrypt
     * @return encrypted data as byte array
     */
    private byte[] encrypt(byte[] bytes) throws IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, KeyStoreException, IllegalBlockSizeException {
        return keyStoreSecurity.encryptDataBytes(bytes);
    }

    /**
     * @param bytes to decrypt
     * @return decrypted data as byte array
     */
    private byte[] decrypt(byte[] bytes) throws IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, KeyStoreException, IllegalBlockSizeException {
        return keyStoreSecurity.decryptDataBytes(bytes);
    }

    private byte[] encrypt(long value) throws IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, KeyStoreException, IllegalBlockSizeException {
        return encrypt(toBytes(value));
    }

    private byte[] encrypt(String value) throws IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, InvalidKeyException, InvalidAlgorithmParameterException, NoSuchPaddingException, BadPaddingException, KeyStoreException, IllegalBlockSizeException {
        return encrypt(toBytes(value));
    }

    private byte[] getCrcValue(String value) {
        return toBytes(crcIntegrity.getCrcValue(toBytes(value)));
    }

//    public DataAndCrcWrapper encryptAndCrc(String value) throws EncryptionFailedException {
//        try {
//            byte[] encrypted = encrypt(toBytes(value));
//            byte[] crcOfEncryptedValue = getCrcValue(encrypted);
//            return new DataAndCrcWrapper(encrypted, encrypt(crcOfEncryptedValue));
//        } catch (InvalidKeyException | IOException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | UnrecoverableKeyException | CertificateException | NoSuchAlgorithmException | KeyStoreException | InvalidAlgorithmParameterException e) {
//            throw new EncryptionFailedException(e.getMessage());
//        }
//    }

    /**
     * @param value object to encrypt
     * @return DataAndCrcWrapper with the encrypted data and encrypted crc over the data
     */
    public DataAndCrcWrapper encryptAndCrc(Object value) throws EncryptionFailedException, DataTypeForEncryptionNotSupportedException {
        try {
            byte[] valueBytes;
            if (value instanceof String) {
                valueBytes = toBytes((String) value);
            } else if (value instanceof Boolean) {
                valueBytes = toBytes((Boolean) value);
            } else if (value instanceof Integer) {
                valueBytes = toBytes((Integer) value);
            } else if (value instanceof Long) {
                valueBytes = toBytes((Long) value);
            } else if (value instanceof Float) {
                valueBytes = toBytes((Float) value);
            } else {
                throw new DataTypeForEncryptionNotSupportedException(value.getClass().getName());
            }
            byte[] encrypted = encrypt(valueBytes);
            byte[] crcOfEncryptedValue = getCrcValue(encrypted);
            return new DataAndCrcWrapper(encrypted, encrypt(crcOfEncryptedValue));
        } catch (InvalidKeyException | IOException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | UnrecoverableKeyException | CertificateException | NoSuchAlgorithmException | KeyStoreException | InvalidAlgorithmParameterException e) {
            throw new EncryptionFailedException(e.getMessage());
        }
    }

    public byte[] encryptAndCrc(Object...values) throws EncryptionFailedException, DataTypeForEncryptionNotSupportedException {
        byte[] dataAsBytesResult = ByteUtils.toBytes(values);
        try {
            byte[] encrypted = encrypt(dataAsBytesResult);
            return getCrcValue(encrypted);

        } catch (InvalidKeyException | IOException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException | UnrecoverableKeyException | CertificateException | NoSuchAlgorithmException | KeyStoreException | InvalidAlgorithmParameterException e) {
            throw new EncryptionFailedException(e.getMessage());
        }
    }

    private byte[] getCrcValue(byte[] value) {
        return toBytes(crcIntegrity.getCrcValue(value));
    }

    /**
     * @param dataAndCrcWrapper should contain the encrypted data and encrypted crc over the data
     * @return the decrypted data as byte array
     * @throws CrcIntegrityFailedException if crc check failed
     * @throws DecryptionFailedException   if decryption failed
     */
    public byte[] decrypt(DataAndCrcWrapper dataAndCrcWrapper) throws CrcIntegrityFailedException, DecryptionFailedException {
        try {
            //in testing this we only experienced  IllegalBlockSizeException
            byte[] decryptedCrc = decrypt(dataAndCrcWrapper.getCrc());

            if (!isCrcEquals(decryptedCrc, getCrcValue(dataAndCrcWrapper.getData()))) {
                throw new CrcIntegrityFailedException();
            }
            //in testing this we only experienced  AEADBadTagException
            return decrypt(dataAndCrcWrapper.getData());
        } catch (IllegalBlockSizeException | IOException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | BadPaddingException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | KeyStoreException e) {
            throw new DecryptionFailedException(e.getMessage());
        }
    }

    /**
     * @param crc1 bytes array argument to compare
     * @param crc2 bytes array argument to compare
     * @return true if crc1 and crc2 are equals
     */
    private boolean isCrcEquals(byte[] crc1, byte[] crc2) {
        return Arrays.equals(crc1, crc2);
    }

}
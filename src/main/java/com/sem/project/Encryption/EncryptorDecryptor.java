package com.sem.project.Encryption;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptorDecryptor {

    public static void cipherOperation(int cipherMode, String key, String inputFile, String outputFile)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
        if (cipherMode != Cipher.ENCRYPT_MODE && cipherMode != Cipher.DECRYPT_MODE) {
            throw new IllegalArgumentException("unsupported cipher mode");
        }

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secKey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(cipherMode, secKey);


        try (FileInputStream inStream = new FileInputStream(inputFile);
             BufferedInputStream inBuf = new BufferedInputStream(inStream))
        {
            byte[] inputBytes = new byte[inBuf.available()];
            inBuf.read(inputBytes);
            byte[] encryptedBytes = cipher.doFinal(inputBytes);
            try (FileOutputStream outStream = new FileOutputStream(outputFile);
                 BufferedOutputStream outBuf = new BufferedOutputStream(outStream))
            {
                outBuf.write(encryptedBytes);
            }
        }
    }

    public static void encrypt(String key, String inputFile, String outputFile)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {

        cipherOperation(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    public static void decrypt(String key, String inputFile, String outputFile)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {

        cipherOperation(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException  {
        encrypt("1234567890123456", "baz.xml", "baz.xml");
        decrypt("1234567890123456", "baz.xml", "text.txt");
    }
}


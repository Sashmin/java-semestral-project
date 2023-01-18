package com.sem.project.Encryption;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class EncryptorDecryptor {

    public static void cipherOperation(int cipherMode, String key, Path inputPath, Path outputPath)
            throws NoSuchAlgorithmException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
        if (cipherMode != Cipher.ENCRYPT_MODE && cipherMode != Cipher.DECRYPT_MODE) {
            throw new IllegalArgumentException("unsupported cipher mode");
        }

        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKey secKey = new SecretKeySpec(key.getBytes(), "AES");
        cipher.init(cipherMode, secKey);

        try (FileInputStream inStream = new FileInputStream(inputPath.toFile());
             BufferedInputStream inBuf = new BufferedInputStream(inStream))
        {
            byte[] inputBytes = new byte[inBuf.available()];
            inBuf.read(inputBytes);
            byte[] encryptedBytes = cipher.doFinal(inputBytes);
            try (FileOutputStream outStream = new FileOutputStream(outputPath.toFile());
                 BufferedOutputStream outBuf = new BufferedOutputStream(outStream))
            {
                outBuf.write(encryptedBytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void encrypt(String key, Path inputFile, Path outputFile)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {

        cipherOperation(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    public static void decrypt(String key, Path inputFile, Path outputFile)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException,
            IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {

        cipherOperation(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidKeyException, IOException, IllegalBlockSizeException, BadPaddingException  {
        encrypt("1234567890123456", Paths.get("D:\\temp\\text.txt"), Paths.get("D:\\temp\\file.enc"));
        encrypt("0987654321098765", Paths.get("D:\\temp\\file.enc"), Paths.get("D:\\temp\\double.enc"));
        //decrypt("12345678901234561234567890123456", Paths.get("file.enc"), Paths.get("arch.7z"));
    }
}


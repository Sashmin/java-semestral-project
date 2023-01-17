package com.sem.project.Decorators;

import com.sem.project.Encryption.EncryptorDecryptor;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;


public class EncryptionDecorator extends DataSourceDecorator {

    public EncryptionDecorator(DataSource source) {
        super(source);
    }

    @Override
    public Path writeData() throws IOException, IllegalBlockSizeException, NoSuchPaddingException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Path path = super.writeData();
        String fileName = "enc" + operationNumber + ".enc";
        Path outputPath = path.toAbsolutePath().getParent().resolve(fileName);
        if (Files.exists(outputPath)) {
            Files.delete(outputPath);
        }
        Path tempResult = Files.createFile(outputPath);
        Scanner keyScan = new Scanner(System.in);
        System.out.println("Enter encryption key (16 or 32 symbols): ");
        String key = keyScan.nextLine();
        EncryptorDecryptor.encrypt(key, path, tempResult);

        Files.delete(path);
        operationNumber++;
        return tempResult;
    }

    @Override
    public String readData() {
        return new String();
    }


}


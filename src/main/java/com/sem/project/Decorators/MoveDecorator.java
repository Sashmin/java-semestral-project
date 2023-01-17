package com.sem.project.Decorators;

import com.sem.project.Encryption.EncryptorDecryptor;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MoveDecorator extends DataSourceDecorator{

    private Path moveToFile;

    MoveDecorator(Path move, DataSource source) {
        super(source);
        moveToFile = move;
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
        Path tempResult = Files.copy(path, moveToFile);

        Files.delete(path);
        return tempResult;
    }
}

package com.sem.project.Decorators;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class MoveDecorator extends DataSourceDecorator{

    private Path moveToFrom;

    public MoveDecorator(Path move, DataSource source) {
        super(source);
        moveToFrom = move;
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
        Path tempResult = Files.copy(path, moveToFrom);

        Files.delete(path);
        return tempResult;
    }

    @Override
    public Path readData(Path path) throws IOException, IllegalBlockSizeException, NoSuchPaddingException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Path tempDir = Files.createTempDirectory(moveToFrom.toAbsolutePath().getParent(), "tempDir");
        Path fileName = moveToFrom.getFileName();
        Path tempFile = tempDir.resolve(fileName);
        Files.copy(moveToFrom, tempFile);
        return super.readData(tempFile);
    }
}

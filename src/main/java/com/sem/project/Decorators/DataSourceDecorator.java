package com.sem.project.Decorators;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class DataSourceDecorator implements DataSource {
    static int operationNumber = 1;
    private DataSource wrappee;

    DataSourceDecorator(DataSource source) {
        this.wrappee = source;
    }

    @Override
    public Path writeData() throws IOException, IllegalBlockSizeException, NoSuchPaddingException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return wrappee.writeData();
    }

    @Override
    public Path readData(Path path) throws IOException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        return wrappee.readData(path);
    }
}

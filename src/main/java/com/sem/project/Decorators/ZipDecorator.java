package com.sem.project.Decorators;

import com.sem.project.Archiving.Zip;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class ZipDecorator extends DataSourceDecorator {
    private int compLevel = 6;

    public ZipDecorator(DataSource source) {
        super(source);
    }

    public int getCompressionLevel() {
        return compLevel;
    }

    public void setCompressionLevel(int value) {
        compLevel = value;
    }

    @Override
    public Path writeData() throws IOException, IllegalBlockSizeException, NoSuchPaddingException,
            NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        Path path = super.writeData();
        String fileName = "zip" + operationNumber + ".zip";
        Path outputPath = path.toAbsolutePath().getParent().resolve(fileName);
        if (Files.exists(outputPath)) {
            Files.delete(outputPath);
        }
        Path tempResult = Files.createFile(outputPath);
        Zip.zipSingleFile(path, tempResult);

        Files.delete(path);
        operationNumber++;
        return tempResult;
    }

    @Override
    public Path readData(Path path) throws IOException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {

        if (Zip.countRegularFiles(path) != 1) {
            throw new IllegalArgumentException("wrong zip file configuration: must contains only 1 file");
        }
        String fileName = "unzip" + operationNumber + ".txt";
        Path tempFile = Zip.unzip(path);

        Files.delete(path);
        super.readData(tempFile);
        return super.readData(tempFile);
    }

    private String compress(String stringData) {
        byte[] data = stringData.getBytes();
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream(512);
            DeflaterOutputStream dos = new DeflaterOutputStream(bout, new Deflater(compLevel));
            dos.write(data);
            dos.close();
            bout.close();
            return Base64.getEncoder().encodeToString(bout.toByteArray());
        } catch (IOException ex) {
            return null;
        }
    }

    private String decompress(String stringData) {
        byte[] data = Base64.getDecoder().decode(stringData);
        try {
            InputStream in = new ByteArrayInputStream(data);
            InflaterInputStream iin = new InflaterInputStream(in);
            ByteArrayOutputStream bout = new ByteArrayOutputStream(512);
            int b;
            while ((b = iin.read()) != -1) {
                bout.write(b);
            }
            in.close();
            iin.close();
            bout.close();
            return new String(bout.toByteArray());
        } catch (IOException ex) {
            return null;
        }
    }
}

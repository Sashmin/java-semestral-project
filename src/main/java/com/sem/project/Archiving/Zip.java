package com.sem.project.Archiving;

import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class Zip {

    public static void zipSingleFile(Path filePath, Path zipFileName)
            throws IOException {

        //File file = new File(filePath);

        if (!Files.exists(filePath)) {
            System.err.println("Please provide a file.");
            return;
        }

        try (ZipOutputStream zos = new ZipOutputStream(
                new FileOutputStream(zipFileName.toFile()));
             FileInputStream fis = new FileInputStream(filePath.toFile());
        ) {

            ZipEntry zipEntry = new ZipEntry(filePath.getFileName().toString());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            zos.closeEntry();
        }

    }

    public static void extractFile(Path zipFile, String fileName, Path outputFile) throws IOException {
        // Wrap the file system in a try-with-resources statement
        // to auto-close it when finished and prevent a memory leak
        try (FileSystem fileSystem = FileSystems.newFileSystem(zipFile, (ClassLoader) null)) {
            Path fileToExtract = fileSystem.getPath(fileName);
            Files.copy(fileToExtract, outputFile, REPLACE_EXISTING);
        }
    }


    public static void main(String[] args) throws IOException {

        zipSingleFile(Paths.get("D:\\temp\\much.txt"), Paths.get("D:\\temp\\ffff.zip"));
    }

}

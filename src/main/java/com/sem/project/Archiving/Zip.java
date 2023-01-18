package com.sem.project.Archiving;



import java.io.*;
import java.nio.file.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
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
        try (FileSystem fileSystem = FileSystems.newFileSystem(zipFile, (ClassLoader) null)) {
            Path fileToExtract = fileSystem.getPath(fileName);
            Files.copy(fileToExtract, outputFile, REPLACE_EXISTING);
        }
    }

    public static Path unzip(Path zipFile) throws IOException {
        byte[] buffer = new byte[1024];
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFile.toFile()));
        ZipEntry entry = zipIn.getNextEntry();
        String fileName = "def.txt";
        if (entry.getName() != null) {
            fileName = entry.getName();
        }
        Path tempFile = zipFile.toAbsolutePath().getParent().resolve(fileName);
        FileOutputStream fileOut = new FileOutputStream(tempFile.toFile());
        int length;
        while ((length = zipIn.read(buffer)) > 0) {
            fileOut.write(buffer, 0, length);
        }
        fileOut.close();
        zipIn.closeEntry();
        zipIn.close();
        return tempFile;
    }

    public static int countRegularFiles(Path zipFilePath) throws IOException {
        ZipFile zipFile = new ZipFile(zipFilePath.toFile());
        final Enumeration<? extends ZipEntry> entries = zipFile.entries();
        int numRegularFiles = 0;
        while (entries.hasMoreElements()) {
            if (! entries.nextElement().isDirectory()) {
                ++numRegularFiles;
            }
            else {
                zipFile.close();
                return 0;
            }
        }
        zipFile.close();
        return numRegularFiles;
    }



    public static void main(String[] args) throws IOException {

        //extractFile(Paths.get("D:\\temp\\fol.zip"), "file.txt", Paths.get("D:\\temp\\unzip.txt"));
        //unzip(Paths.get("D:\\temp\\fold.zip"), Paths.get("D:\\temp\\adsd.txt"));
    }

}

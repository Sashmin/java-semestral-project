package com.sem.project.Decorators;

import java.io.*;
import java.nio.file.*;
//import java.nio.file.StandardCopyOption;

public class FileDataSource implements DataSource {
    private Path filePath;

    public FileDataSource(Path path) {
        this.filePath = path;
    }

    @Override
    public Path writeData() throws IOException{
        return filePath;
    }

    @Override
    public Path readData(Path path) throws IOException {

        Path tempDir = path.toAbsolutePath().getParent();
        Path resultFile = path.toAbsolutePath().getParent().getParent().resolve("result.xml");
        Files.copy(path, resultFile, StandardCopyOption.REPLACE_EXISTING);
        deleteDirectory(tempDir);


        return resultFile;
    }

    void deleteDirectory(Path path) throws IOException {
        if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
                for (Path entry : entries) {
                    deleteDirectory(entry);
                }
            }
        }
        Files.delete(path);
    }
}

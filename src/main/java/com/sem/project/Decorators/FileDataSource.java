package com.sem.project.Decorators;

import java.io.*;
import java.nio.file.*;

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
    public String readData() {
        return new String();
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

package com.sem.project.ReadWrite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class TXTReadWrite {

    public static ArrayList<String> readTXT(Path path) throws FileNotFoundException {
        Scanner scan = new Scanner(path.toFile());
        ArrayList<String> list = new ArrayList<>();
        while (scan.hasNextLine()) {
            list.add(scan.nextLine());
        }
        return list;
    }

    public static Path writeTXT(ArrayList<String> list, String fileName) throws IOException {
        Path destDir = Paths.get("tempDir").toAbsolutePath();
        if (!Files.exists(destDir) || !Files.isDirectory(destDir)) {
            Files.createDirectory(destDir);
        }
        Path dest = destDir.toAbsolutePath().resolve(fileName);
        if (Files.exists(dest)){
            Files.delete(dest);
        }
        Files.createFile(dest);
        try (FileWriter writer = new FileWriter(dest.toFile())) {
            for (String str: list) {
                writer.write(str + "\n");
            }
        }
        return dest;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> list = readTXT(Path.of("D:\\JavaProjects\\java-semestral-project\\inputFiles\\in.txt"));
        writeTXT(list, "new.txt");
    }
}

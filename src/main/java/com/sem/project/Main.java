package com.sem.project;

import com.sem.project.Decorators.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome!\n");

        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the path to the file:");
        Path sourcePath = Paths.get(scan.nextLine().replace("\\", "\\\\"));

        DataSource readDec = new FileDataSource(sourcePath);

        while (true) {
            System.out.println("\nThe file was (enter number):");
            System.out.println("1 - archived");
            System.out.println("2 - encrypted");
            System.out.println("other - leave cycle");
            String answer = scan.nextLine();
            if (answer.equals("1")) {
                readDec = new ZipDecorator(readDec);
            }
            else if (answer.equals("2")) {
                readDec = new EncryptionDecorator(readDec);
            }
            else {
                break;
            }

        }

        readDec = new MoveDecorator(sourcePath, readDec);

        Path newSource = Paths.get("./");
        try {
            newSource = readDec.readData(Paths.get("./"));
        } catch (IllegalBlockSizeException | InvalidKeyException | NoSuchPaddingException | IOException |
                 NoSuchAlgorithmException | BadPaddingException e) {

            System.out.println("an error occurred during performing operations");
            return;
        } finally {
            System.out.println(newSource.toString());
        }
    }
}

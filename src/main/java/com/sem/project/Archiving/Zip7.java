package com.sem.project.Archiving;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;
import org.apache.commons.compress.archivers.sevenz.SevenZOutputFile;

import java.io.*;

public class Zip7 {
    public static void zip() {
        try{
            BufferedInputStream instream = new BufferedInputStream(new FileInputStream("d:/temp/test.txt"));

            SevenZOutputFile sevenZOutput = new SevenZOutputFile(new File("d:/temp/7ztest.7z"));
            SevenZArchiveEntry entry = sevenZOutput.createArchiveEntry(new File("d:/temp/test.txt"),"blah.txt");
            sevenZOutput.putArchiveEntry(entry);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = instream.read(buffer)) > 0) {sevenZOutput.write(buffer, 0, len);}

            sevenZOutput.closeArchiveEntry();
            sevenZOutput.close();
            instream.close();
        }catch(IOException ioe) {
            System.out.println(ioe.toString());

        }
    }

    public static void decompress(String in, File destination) throws IOException {
        SevenZFile sevenZFile = new SevenZFile(new File(in));
        SevenZArchiveEntry entry;
        while ((entry = sevenZFile.getNextEntry()) != null){
            if (entry.isDirectory()){
                continue;
            }
            File curfile = new File(destination, entry.getName());
            File parent = curfile.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(curfile);
            byte[] content = new byte[(int) entry.getSize()];
            sevenZFile.read(content, 0, content.length);
            out.write(content);
            out.close();
        }
    }

    public static void main(String[] args) throws IOException{
        File dest = new File("./");
        decompress("archivet.7z", dest);
    }
}

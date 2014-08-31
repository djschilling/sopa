package de.sopa.helper;

/**
 * @author David Schilling - davejs92@gmail.com
 */

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class FileHandler {

    private final Context context;

    public FileHandler(Context context) {
        this.context = context;
    }

    
    public void writeToFile(String filename, String[] strings) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filename);
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            fileOutputStream.write((string).getBytes());
            if (i < strings.length ) {
                fileOutputStream.write('\n');
            }
        }
        fileOutputStream.close();
    }

    
    public String[] readFromFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(new File(filename));
        int content;
        StringBuilder lineContent = new StringBuilder();
        while ((content = fileInputStream.read()) != -1) {
            if ((char) content == '\n') {
                lines.add(lineContent.toString());
                lineContent = new StringBuilder();
            } else {
                lineContent.append((char) content);
            }

        }
        if(lineContent.length() != 0) {
            lines.add(lineContent.toString());
        }
        fileInputStream.close();
        return lines.toArray(new String[lines.size()]);
    }


    public String[] getFilenamesInFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] fileArray = folder.listFiles();
        String[] filenames = new String[fileArray.length];
        for(int i = 0; i < fileArray.length; i++) {
            filenames[i] = fileArray[i].getName();
        }
        return filenames;
    }
}
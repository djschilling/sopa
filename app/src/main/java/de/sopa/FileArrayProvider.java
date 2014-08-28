package de.sopa;

/**
 * @author David Schilling - davejs92@gmail.com
 */
import android.content.Context;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class FileArrayProvider {

    public String[] readLines(String filename, Context context) throws IOException {
        ArrayList<String> levelStrings = new ArrayList<>();
        try {
            FileInputStream fileInputStream = context.openFileInput("kuchen.test");
            int content;
            int line = 0;
            levelStrings.add(0,new String());
            while ((content = fileInputStream.read()) != -1) {
                if((char)content == '\n') {
                    line++;
                    levelStrings.add(line, new String());
                } else {
                    levelStrings.set(line,levelStrings.get(line) + (char)content);
                }

                // convert to char and display it
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.print("FileNotFound");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return levelStrings.toArray(new String[levelStrings.size()]);
    }
    public void writeLines(String filename, Context context, String[] strings) throws IOException {

        FileOutputStream fileOutputStream =  context.openFileOutput(filename, Context.MODE_PRIVATE);
        for (int i = 0; i<strings.length; i++) {
            String string = strings[i];
            fileOutputStream.write((string).getBytes());
            if(i < strings.length-1) {
                fileOutputStream.write('\n');
            }
        }
        fileOutputStream.close();

    }


}
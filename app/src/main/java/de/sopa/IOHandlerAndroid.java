package de.sopa;

/**
 * @author David Schilling - davejs92@gmail.com
 */
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.*;
import java.util.ArrayList;


public class IOHandlerAndroid implements IOHandler {

    private Context context;
    public IOHandlerAndroid(Context context) {
        this.context = context;
        Log.i("WRITE", String.valueOf(isExternalStorageWritable()));
    }
    @Override
    public void writeToFile(String filename, String[] strings) throws IOException {
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

    @Override
    public String[] readFromFile(String filename) {
        ArrayList<String> levelStrings = new ArrayList<>();
        try {
            FileInputStream fileInputStream = context.openFileInput(filename);
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
    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
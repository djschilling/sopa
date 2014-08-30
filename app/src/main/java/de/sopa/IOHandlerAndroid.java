package de.sopa;

/**
 * @author David Schilling - davejs92@gmail.com
 */

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import de.sopa.manager.ResourcesManager;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class IOHandlerAndroid implements IOHandler {

    private final Context context;

    public IOHandlerAndroid() {
        this.context = ResourcesManager.getInstance().activity.getApplicationContext();
        Log.i("WRITE", String.valueOf(isExternalStorageWritable()));
    }

    @Override
    public void writeToFile(String filename, String[] strings, int fileCreationMode) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("/sdcard/sopa" + filename);
        //FileOutputStream fileOutputStream = context.openFileOutput(filename, fileCreationMode);
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            fileOutputStream.write((string).getBytes());
            if (i < strings.length - 1) {
                fileOutputStream.write('\n');
            }
        }
        fileOutputStream.close();
    }

    @Override
    public String[] readFromPrivateFile(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream("/sdcard/sopa" + filename);
        //FileInputStream fileInputStream = context.openFileInput(filename);
        int content;
        StringBuilder lineContent = new StringBuilder();
        while ((content = fileInputStream.read()) != -1) {
            if ((char) content == '\n') {
                lines.add(lineContent.toString());
                lineContent = new StringBuilder();
            } else {
                lineContent.append(content);
            }

        }
        return lines.toArray(new String[lines.size()]);
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
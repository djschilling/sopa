package de.sopa;

/**
 * @author David Schilling - davejs92@gmail.com
 */

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import de.sopa.manager.ResourcesManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LevelFileHandler implements IOHandler {

    private final Context context;
    private static final String LEVEL_FOLDER = "/sdcard/sopa/levels/";

    public LevelFileHandler() {
        this.context = ResourcesManager.getInstance().activity.getApplicationContext();
        Log.i("WRITE", String.valueOf(isExternalStorageWritable()));
        File folder = new File(LEVEL_FOLDER);
        if(!folder.exists()) {
            folder.mkdirs();
        }
    }

    @Override
    public void writeToFile(String filename, String[] strings, int fileCreationMode) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(LEVEL_FOLDER + filename);
        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            fileOutputStream.write((string).getBytes());
            if (i < strings.length ) {
                fileOutputStream.write('\n');
            }
        }
        fileOutputStream.close();
    }

    @Override
    public String[] readFromPrivateFile(String filename) throws IOException {
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

    @Override
    public String[] getAvailableLevels() {
        File folder = new File(LEVEL_FOLDER);
        File[] fileArray = folder.listFiles();
        String[] strings = new String[fileArray.length];
        for(int i = 0; i<fileArray.length; i++) {
            strings[i] = fileArray[i].getAbsolutePath();
            fileArray[i].getName();
        }
        return strings;
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
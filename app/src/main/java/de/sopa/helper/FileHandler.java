package de.sopa.helper;

/**
 * @author David Schilling - davejs92@gmail.com
 * @author Raphael Schilling
 */

import android.content.Context;

import de.sopa.manager.ResourcesManager;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;


class FileHandler {

    private final Context context;

    FileHandler(Context context) {

        this.context = context;
    }

    String[] readFromFile(String filename) throws IOException {

        List<String> lines = new ArrayList<>();
        InputStream inputStream = ResourcesManager.getInstance().activity.getAssets().open(filename);
        int content;
        StringBuilder lineContent = new StringBuilder();

        while ((content = inputStream.read()) != -1) {
            if ((char) content == '\n') {
                lines.add(lineContent.toString());
                lineContent = new StringBuilder();
            } else {
                lineContent.append((char) content);
            }
        }

        if (lineContent.length() != 0) {
            lines.add(lineContent.toString());
        }

        inputStream.close();

        return lines.toArray(new String[lines.size()]);
    }


    String[] getFilenamesInFolder(String folderPath) {

        String[] fileArray = null;

        try {
            fileArray = context.getAssets().list(folderPath.substring(0, folderPath.length() - 1));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileArray;
    }
}

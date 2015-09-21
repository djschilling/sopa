package de.sopa.helper;

import android.content.Context;
import android.content.SharedPreferences;

import android.os.Environment;

import android.util.Log;

import de.sopa.manager.ResourcesManager;

import de.sopa.model.game.Level;

import java.io.IOException;


/**
 * @author  David Schilling - davejs92@gmail.com
 * @author  Raphael Schilling
 */
public class LevelFileService {

    private static final String LEVEL_BASE_PATH = "levels/";
    private final FileHandler fileHandler;
    private LevelTranslator levelTranslator;

    public LevelFileService(Context context) {

        levelTranslator = new LevelTranslator();
        fileHandler = new FileHandler(context);
    }

    public Level getLevel(Integer id) {

        String levelFilename = null;

        try {
            levelFilename = LEVEL_BASE_PATH + id + ".lv";

            return levelTranslator.fromString(fileHandler.readFromFile(levelFilename));
        } catch (IOException e) {
            throw new LevelServiceException("Not possible to read GameFiled from " + levelFilename, e);
        }
    }


    public Integer[] getAvailableLevelIds() {

        String[] filenamesInFolder = fileHandler.getFilenamesInFolder(LEVEL_BASE_PATH);
        Integer[] levelIds = new Integer[filenamesInFolder.length];

        for (int i = 0; i < filenamesInFolder.length; i++) {
            levelIds[i] = Integer.parseInt(filenamesInFolder[i].split("\\.")[0]);
        }

        return levelIds;
    }
}

package de.sopa;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import de.sopa.manager.ResourcesManager;
import de.sopa.model.levelCreator;
import de.sopa.model.Level;
import de.sopa.model.LevelServiceException;
import java.io.IOException;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public class LevelFileService {
    private final FileHandler fileHandler;
    private levelCreator levelCreator;
    private static final String LEVEL_BASE_PATH = "/sdcard/sopa/levels/";
    private final static String LEVEL_COUNT = "LEVEL_COUNT";

    public LevelFileService(Context context) {
        levelCreator = new levelCreator();
        this.fileHandler = new FileHandler(context);
    }

    public Level getLevel(Integer id) {
        String levelFilename = null;
        try {
            levelFilename = LEVEL_BASE_PATH + id + ".lv";
            return levelCreator.fromString(fileHandler.readFromFile(levelFilename));
        } catch (IOException e) {
            throw new LevelServiceException("Not possible to read GameFiled from " + levelFilename, e);
        }
    }

    public Integer saveGameField(Level level) throws IOException {
        SharedPreferences settings = ResourcesManager.getInstance().activity.getPreferences(0);
        Integer count = settings.getInt(LEVEL_COUNT, 0);
        count++;
        String levelFilename = LEVEL_BASE_PATH + count + ".lv";
        fileHandler.writeToFile(levelFilename, levelCreator.fromGameField(level));
        Log.i("Level saved as ", levelFilename);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(LEVEL_COUNT, count);
        editor.commit();
        return count;
    }
    public Integer updateGameField(Level level, Integer id) throws IOException {
        String levelFilename = LEVEL_BASE_PATH + id + ".lv";
        fileHandler.writeToFile(levelFilename, levelCreator.fromGameField(level));
        Log.i("Level updated as ", String.valueOf(id + ".lv"));
        return id;
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

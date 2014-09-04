package de.sopa.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;
import de.sopa.manager.ResourcesManager;
import de.sopa.model.Level;
import java.io.IOException;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public class LevelFileService {
    private final FileHandler fileHandler;
    private LevelCreator LevelCreator;
    private static final String LEVEL_BASE_PATH = "levels/";
    private static final String LEVEL_SAVE_PATH = Environment.getExternalStorageDirectory().getPath() + "/sopa/levels/";
    private final static String LEVEL_COUNT = "LEVEL_COUNT";

    public LevelFileService(Context context) {
        LevelCreator = new LevelCreator();
        this.fileHandler = new FileHandler(context);
    }

    public Level getLevel(Integer id) {
        String levelFilename = null;
        try {
            levelFilename = LEVEL_BASE_PATH + id + ".lv";
            return LevelCreator.fromString(fileHandler.readFromFile(levelFilename));
        } catch (IOException e) {
            throw new LevelServiceException("Not possible to read GameFiled from " + levelFilename, e);
        }
    }

    public Integer saveGameField(Level level) throws IOException {
        SharedPreferences settings = ResourcesManager.getInstance().activity.getPreferences(0);
        Integer count = settings.getInt(LEVEL_COUNT, 0);
        count++;
        level.setId(count);
        String levelFilename = LEVEL_SAVE_PATH + count + ".lv";
        fileHandler.writeToFile(levelFilename, LevelCreator.fromGameField(level));
        Log.i("Level saved as ", levelFilename);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(LEVEL_COUNT, count);
        editor.commit();
        return count;
    }

    public Integer updateGameField(Level level, Integer id) throws IOException {
        String levelFilename = LEVEL_BASE_PATH + id + ".lv";
        fileHandler.writeToFile(levelFilename, LevelCreator.fromGameField(level));
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

package de.sopa.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import de.sopa.IOHandler;
import de.sopa.LevelFileHandler;
import de.sopa.manager.ResourcesManager;

import java.io.IOException;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public class GameFieldHandler {
    private IOHandler ioHandler;
    private FieldHandler fieldHandler;
    private final static String LEVEL_COUNT = "LEVEL_COUNT";

    public GameFieldHandler() {
        ioHandler = new LevelFileHandler();
        fieldHandler = new FieldHandler();
    }

    public Level getLevel(String filename) {
        try {
            return fieldHandler.fromString(ioHandler.readFromPrivateFile(filename));
        } catch (IOException e) {
            throw new LevelServiceException("Not possible to read GameFiled from " + filename, e);
        }
    }

    public Integer saveGameField(Level level) throws IOException {
        SharedPreferences settings = ResourcesManager.getInstance().activity.getPreferences(0);
        Integer count = settings.getInt(LEVEL_COUNT, 0);
        count++;
        ioHandler.writeToFile(count + ".lv", fieldHandler.fromGameField(level), Context.MODE_PRIVATE);
        Log.i("Level saved as ", String.valueOf(count + ".lv"));
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(LEVEL_COUNT, count);
        editor.commit();
        return count;
    }
    public Integer updateGameField(Level level, Integer id) throws IOException {
        ioHandler.writeToFile(id + ".lv", fieldHandler.fromGameField(level), Context.MODE_PRIVATE);
        Log.i("Level updated as ", String.valueOf(id + ".lv"));
        return id;
    }
    public Integer[] getAvailableLevelIds() {
        return ioHandler.getAvailableLevelIds();
    }
}

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

    public GameField getGameField(String filename) {
        try {
            return fieldHandler.fromString(ioHandler.readFromPrivateFile(filename));
        } catch (IOException e) {
            throw new LevelNotFoundException("Not possible to read GameFiled from " + filename, e);
        }
    }

    public int saveGameField(GameField gameField) throws IOException {
        SharedPreferences settings = ResourcesManager.getInstance().activity.getPreferences(0);
        int count = settings.getInt(LEVEL_COUNT,0);
        count++;
        ioHandler.writeToFile(count + ".lv", fieldHandler.fromGameField(gameField), Context.MODE_PRIVATE);
        Log.i("Level saved as ", String.valueOf(count + ".lv"));
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(LEVEL_COUNT, count);
        editor.commit();
        return count;
    }

}

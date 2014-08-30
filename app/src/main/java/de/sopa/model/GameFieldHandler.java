package de.sopa.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import de.sopa.IOHandler;
import de.sopa.IOHandlerAndroid;
import de.sopa.manager.ResourcesManager;

import java.io.IOException;

/**
 * @author David Schilling - davejs92@gmail.com
 */
public class GameFieldHandler {
    private IOHandler ioHandler;
    private FieldHandler fieldHandler;
    public GameFieldHandler() {
        ioHandler = new IOHandlerAndroid();
        fieldHandler = new FieldHandler();
    }

    public GameField getGameField(int id) {
        try {
            return fieldHandler.fromString(ioHandler.readFromPrivateFile(id + ".lv"));
        } catch (IOException e) {
            throw new LevelNotFoundException("Not possible to read GameFiled from " + id + ".lv", e);
        }
    }

    public int saveGameField(GameField gameField) throws IOException {
        SharedPreferences settings = ResourcesManager.getInstance().activity.getPreferences(0);
        int count = settings.getInt("count",0);
        count++;
        Log.i("Count", String.valueOf(count));
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("count", count);
        editor.commit();
        ioHandler.writeToFile(count + ".lv", fieldHandler.fromGameField(gameField), Context.MODE_PRIVATE);
        return count;
    }

}

package de.sopa.database;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * @author  David Schilling - davejs92@gmail.com
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sopa";
    private static final int DATABASE_VERSION = 2;

    public MySQLiteHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        LevelInfoTable.onCreate(db);
        LevelInfoTable.createScoreTable(db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion == 1 && newVersion == 2) {
            LevelInfoTable.createScoreTable(db);
        } else {
            throw new UnsupportedOperationException();
        }
    }
}

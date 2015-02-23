package de.sopa.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * David Schilling - davejs92@gmail.com
 */
public class LevelInfoTable {

    public static final String TABLE_LEVEL_INFO = "level_info";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LOCKED = "locked";
    public static final String COLUMN_FEWEST_MOVES = "fewest_moves";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_LEVEL_INFO + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_LOCKED
            + " integer not null," + COLUMN_FEWEST_MOVES + " integer);";


    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

}

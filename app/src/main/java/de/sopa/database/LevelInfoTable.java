package de.sopa.database;

import android.database.sqlite.SQLiteDatabase;


/**
 * @author  David Schilling - davejs92@gmail.com
 */
class LevelInfoTable {

    static final String TABLE_LEVEL_INFO = "level_info";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_LOCKED = "locked";
    static final String COLUMN_FEWEST_MOVES = "fewest_moves";
    static final String COLUMN_STARS = "stars";
    static final String TABLE_SCORE = "score";
    static final String COLUMN_SCORE_POINTS = "points";
    static final String COLUMN_SCORE_SOLVED_LEVELS = "solved_levels";

    private static final String DATABASE_CREATE = "create table "
        + TABLE_LEVEL_INFO + "(" + COLUMN_ID
        + " integer primary key autoincrement, " + COLUMN_LOCKED
        + " integer not null," + COLUMN_FEWEST_MOVES + " integer, " + COLUMN_STARS + " integer not null);";

    private static final String TABLE_SCORE_CREATE = "create table "
        + TABLE_SCORE + "(" + COLUMN_ID
        + " integer primary key autoincrement, " + COLUMN_SCORE_POINTS
        + " integer not null," + COLUMN_SCORE_SOLVED_LEVELS + " integer not null);";

    private LevelInfoTable() {}

    static void onCreate(SQLiteDatabase database) {

        database.execSQL(DATABASE_CREATE);
    }


    static void createScoreTable(SQLiteDatabase database) {

        database.execSQL(TABLE_SCORE_CREATE);
    }
}

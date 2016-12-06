package com.example.z.mypantry20;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rushabh on 11/26/16.
 */

public class PantryDbHelper extends SQLiteOpenHelper {
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE IF NOT EXISTS " + PantryContract.Pantry.TABLE_NAME + " (" +
                    PantryContract.Pantry._ID + " INTEGER PRIMARY KEY," +
                    PantryContract.Pantry.ITEM_NAME + " VARCHAR(40) " + COMMA_SEP +
                    PantryContract.Pantry.CATEGORY_ID + " INTEGER " + COMMA_SEP +
                    PantryContract.Pantry.AMOUNT_REMAINING + " VARCHAR(40) " + COMMA_SEP +
                    PantryContract.Pantry.AMOUNT_REMAINING_UNIT + " REAL " + " )";

    private static final String SQL_CREATE_CATEGORIES =
            "CREATE TABLE IF NOT EXISTS " + PantryContract.Category.TABLE_NAME + " (" +
                    PantryContract.Pantry._ID + " INTEGER PRIMARY KEY," +
                    PantryContract.Category.NAME + " VARCHAR(40) " + COMMA_SEP +
                    PantryContract.Category.DESCRIPTION + " VARCHAR(100)) ";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PantryContract.Pantry.TABLE_NAME;

    private static final String SQL_DELETE_CATEGORIES =
            "DROP TABLE IF EXISTS " + PantryContract.Category.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "Pantry.db";

    public PantryDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        db.execSQL(SQL_CREATE_CATEGORIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(SQL_DELETE_CATEGORIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}

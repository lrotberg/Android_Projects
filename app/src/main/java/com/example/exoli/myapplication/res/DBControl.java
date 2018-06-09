package com.example.exoli.myapplication.res;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBControl extends SQLiteOpenHelper {

    private static final String TABLE = "table_scores";
    private static final String COL_NAME = "name";
    private static final String COL_DIFF = "diff";
    private static final String COL_SCORE = "score";
    private static final String COL_LATITUDE = "lat";
    private static final String COL_LONGITUDE = "lng";


    public DBControl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, TABLE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL_NAME + " TEXT, " + COL_SCORE + " LONG, "
                + COL_DIFF + " INTEGER, " + COL_LATITUDE + " REAL, " + COL_LONGITUDE + " REAL )";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP IF TABLE EXISTS " + TABLE;
        db.execSQL(dropTable);
    }

    public Cursor highestScores(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE + " ORDER BY " + COL_SCORE + " DESC LIMIT 10";
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public boolean addScore(String name, int diff, long score, double lat, double lng){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_SCORE, score);
        contentValues.put(COL_DIFF, diff);
        contentValues.put(COL_LATITUDE , lat);
        contentValues.put(COL_LONGITUDE, lng);

        long result = db.insert(TABLE,null,contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

}

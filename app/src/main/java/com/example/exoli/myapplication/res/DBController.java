package com.example.exoli.myapplication.res;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBController extends SQLiteOpenHelper {

    private static final String TAG = "DBController";
    private static final String TABLE = "table_scores";
    private static final String COL_NAME = "name";
    private static final int COL_NUM_NAME = 1;
//    private static final String COL_DIFF = "diff";
//    private static final int COL_NUM_DIFF = 2;
    private static final String COL_SCORE = "score";
    private static final int COL_NUM_SCORE = 2;
    private static final String COL_LATITUDE = "lat";
    private static final int COL_NUM_LATITUDE = 3;
    private static final String COL_LONGITUDE = "lng";
    private static final int COL_NUM_LONGITUDE = 4;
    private static final String NUM_OF_SCORES_TO_SHOW = "15";

    public static int getColNumName() {
        return COL_NUM_NAME;
    }

//    public static int getColNumDiff() {
//        return COL_NUM_DIFF;
//    }

    public static int getColNumScore() {
        return COL_NUM_SCORE;
    }

    public static int getColNumLatitude() {
        return COL_NUM_LATITUDE;
    }

    public static int getColNumLongitude() {
        return COL_NUM_LONGITUDE;
    }

    public DBController(Context context) {
        super(context, TABLE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+TABLE+" (ID INTEGER PRIMARY KEY AUTOINCREMENT, "+COL_NAME+" TEXT, "+COL_SCORE+" REAL,"
                +COL_LATITUDE+" REAL, "+COL_LONGITUDE+" REAL )";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP IF TABLE EXISTS "+TABLE;
        db.execSQL(dropTable);
    }

    public Cursor highestScores(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+TABLE+" ORDER BY "+COL_SCORE +" DESC LIMIT " + NUM_OF_SCORES_TO_SHOW;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public boolean addScore(String name, float score, double lat, double lng){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_SCORE, score);
        //contentValues.put(COL_DIFF, diff);
        contentValues.put(COL_LATITUDE , lat);
        contentValues.put(COL_LONGITUDE, lng);

        Log.d(TAG, "addData: Adding "+name+" to "+TABLE);

        long result = db.insert(TABLE,null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

}

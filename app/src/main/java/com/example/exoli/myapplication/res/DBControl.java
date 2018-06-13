package com.example.exoli.myapplication.res;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.exoli.myapplication.R;

public class DBControl extends SQLiteOpenHelper {

    private static final String TABLE = "table_scores";
    private static final String COL_NAME = "name";
    private static final int COL_NUM_NAME = 1;
    private static final String COL_DIFF = "diff";
    private static final int COL_NUM_DIFF = 2;
    private static final String COL_SCORE = "score";
    private static final int COL_NUM_SCORE = 3;
    private static final String COL_LATITUDE = "lat";
    private static final int COL_NUM_LATITUDE = 4;
    private static final String COL_LONGITUDE = "lng";
    private static final int COL_NUM_LONGITUDE = 5;
    private static final String NUM_OF_SCORES_TO_SHOW = "15";

    public static int getColNumName() {
        return COL_NUM_NAME;
    }

    public static int getColNumDiff() {
        return COL_NUM_DIFF;
    }

    public static int getColNumScore() {
        return COL_NUM_SCORE;
    }

    public static int getColNumLatitude() {
        return COL_NUM_LATITUDE;
    }

    public static int getColNumLongitude() {
        return COL_NUM_LONGITUDE;
    }

    public DBControl(Context context) {
        super(context, TABLE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = R.string.create_table + " " + TABLE + "(" + R.string.primary_key_incorrect
                + "," + COL_NAME + " " + R.string.sql_text + ", " + COL_SCORE + " " + R.string.sql_float
                + ", " + COL_DIFF + " " + R.string.sql_integer + ", " + COL_LATITUDE + " "
                + R.string.sql_real + ", " + COL_LONGITUDE + " " + R.string.sql_real + ")";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable =  R.string.drop_table + " " + TABLE;
        db.execSQL(dropTable);
    }

    public Cursor highestScores(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = R.string.sql_select + " " + R.string.sql_astrix + " "
                + R.string.sql_from + TABLE + " " + R.string.sql_order_by + " " + COL_SCORE
                + " " + R.string.sql_decent + " " + R.string.sql_limit + NUM_OF_SCORES_TO_SHOW;
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

        long result = db.insert(TABLE,null, contentValues);

        if(result == -1)
            return false;
        else
            return true;
    }

}

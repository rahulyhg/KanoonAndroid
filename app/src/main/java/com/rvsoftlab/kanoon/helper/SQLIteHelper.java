package com.rvsoftlab.kanoon.helper;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RVishwakarma on 2/7/2018.
 */

public class SQLIteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "kanoon";
    private static final String TABLE_NAME = "user";

    //column name
    private static final String ID = "id";
    private static final String MOBILE = "mobile";

    //region CREATE USER TABLE
    private static final String CREATE_USER_TABLE = "CREATE TABLE "+ TABLE_NAME + "("
            + ID + " INTEGER PRIMARY KEY,"
            + MOBILE + " TEXT )";
    //endregion

    public SQLIteHelper(Activity activity){
        super(activity,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addMobile(String mobile){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(MOBILE,mobile);
        db.insert(TABLE_NAME,null,value);
        db.close();
    }

    public String getMobile(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT "+MOBILE+" FROM "+TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery,null);
        if (cursor!=null){
            cursor.moveToFirst();
            if (cursor.getCount()>0){
                return cursor.getString(cursor.getColumnIndex(MOBILE));
            }else {
                return "";
            }
        }else {
            return "";
        }
    }
}

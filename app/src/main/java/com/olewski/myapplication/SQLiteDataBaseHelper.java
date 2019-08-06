package com.olewski.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Todo.db";
    //Task
    public static final String TABLE_TASK_NAME = "TASK";
    public static final String COL_ID_TASK = "ID";
    public static final String COL_TEXT_TASK = "TEXT";
    public static final String COL_ISDONE_TASK = "ISDONE";
    public static final String COL_LIST_ID_TASK = "LISTID";

    //List

    public static final String TABLE_LIST_NAME = "LIST";
    public static final String COL_ID_LIST = "ID";
    public static final String COL_TEXT_LIST = "TEXT";


    public SQLiteDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE  TABLE " + TABLE_LIST_NAME + " (" + COL_ID_LIST + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TEXT_LIST + " TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_TASK_NAME + " (" + COL_ID_TASK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TEXT_TASK + " TEXT, " + COL_ISDONE_TASK + " BOOLEAN, " +
                COL_LIST_ID_TASK + " INTEGER, " +
                "CONSTRAINT fk_list " +
                "FOREIGN KEY ( " + COL_LIST_ID_TASK + " ) " +
                " REFERENCES " + TABLE_LIST_NAME + "(" + COL_ID_TASK + ") )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean newList(String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TEXT_LIST, text);
        long result = db.insert(TABLE_LIST_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllList() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_LIST_NAME, null);
    }
}

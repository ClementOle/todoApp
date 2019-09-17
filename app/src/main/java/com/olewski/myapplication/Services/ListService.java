package com.olewski.myapplication.Services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.olewski.myapplication.Database.SQLiteDataBaseHelper;

public class ListService {

    public static final String TABLE_LIST_NAME = "LIST";
    public static final String COL_ID_LIST = "ID";
    public static final String COL_TEXT_LIST = "TEXT";

    private SQLiteDataBaseHelper sqLiteDataBaseHelper;

    public boolean newList(String text) {
        SQLiteDatabase db = sqLiteDataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TEXT_LIST, text);
        long result = db.insert(TABLE_LIST_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllList() {
        SQLiteDatabase db = sqLiteDataBaseHelper.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_LIST_NAME, null);
    }
}

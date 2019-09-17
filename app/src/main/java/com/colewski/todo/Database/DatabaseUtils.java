package com.colewski.todo.Database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseUtils extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "todo.db";


    public DatabaseUtils(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tasks (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEXT TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
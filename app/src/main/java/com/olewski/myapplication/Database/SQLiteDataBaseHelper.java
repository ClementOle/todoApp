package com.olewski.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.olewski.myapplication.Model.Task;

import static com.olewski.myapplication.Services.ListService.*;
import static com.olewski.myapplication.Services.TaskService.*;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Todo.db";


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

}

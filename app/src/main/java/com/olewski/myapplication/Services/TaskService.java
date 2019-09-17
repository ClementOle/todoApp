package com.olewski.myapplication.Services;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.olewski.myapplication.Database.SQLiteDataBaseHelper;
import com.olewski.myapplication.Model.Task;

public class TaskService {

    public static final String TABLE_TASK_NAME = "TASK";
    public static final String COL_ID_TASK = "ID";
    public static final String COL_TEXT_TASK = "TEXT";
    public static final String COL_ISDONE_TASK = "ISDONE";
    public static final String COL_LIST_ID_TASK = "LISTID";

    private SQLiteDataBaseHelper sqLiteDataBaseHelper;

    public boolean newTask(Task task) {
        SQLiteDatabase db = sqLiteDataBaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TEXT_TASK, task.getText());
        contentValues.put(COL_ISDONE_TASK, task.getIsDone());
        contentValues.put(COL_LIST_ID_TASK, task.getListId());
        long result = db.insert(TABLE_TASK_NAME, null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllTasks() {
        SQLiteDatabase db = sqLiteDataBaseHelper.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TASK_NAME, null);
    }

    public Cursor getAllTasksByListId(Integer listId) {
        SQLiteDatabase db = sqLiteDataBaseHelper.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_TASK_NAME + " WHERE " + COL_LIST_ID_TASK + " = " + listId, null);
    }

    public boolean updateTask(Task task) {
        SQLiteDatabase db = sqLiteDataBaseHelper.getWritableDatabase();
        // db.rawQuery("UPDATE ")
        return true;
    }
}

package com.colewski.todo.Database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.colewski.todo.Models.Task;

public class TaskService {

    private DatabaseUtils databaseUtils;

    public boolean postTask(Task task) {
        SQLiteDatabase db = databaseUtils.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("test", task.getText());
        long result = db.insert("task", null, contentValues);
        return result != -1;
    }
}

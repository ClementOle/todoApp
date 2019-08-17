package com.olewski.myapplication.Activity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.olewski.myapplication.R;
import com.olewski.myapplication.SQLiteDataBaseHelper;
import com.olewski.myapplication.model.Task;

import java.util.ArrayList;
import java.util.List;


public class TaskActivity extends AppCompatActivity {
    public static Integer idList = 0;
    SQLiteDataBaseHelper db;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        editText = findViewById(R.id.editText);
        editText.setFocusable(true);

        db = new SQLiteDataBaseHelper(this);

        //UtilFilesStorage.createFile(this, "dataTodo.json");

        //List<Task> tasks = TaskService.getTaskFromJson(getApplicationContext(), TaskActivity.this, "dataTodo.json");
        /*if(tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                if (task.getListId().equals(TaskActivity.idList))
                    TaskService.showTask(TaskActivity.this, getApplicationContext(), task, "dataTodo.json");
            }
        }*/

        Button postButton = findViewById(R.id.button2);


        postButton.setOnClickListener(postTask());
        showAllTaskByCurrentListId();

    }


    /*protected View.OnClickListener configurePostButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskService.saveTaskInJson(getApplicationContext(), TaskActivity.this, "dataTodo.json");
                //TaskService.getTaskFromJson(getApplicationContext(), TaskActivity.this, "dataTodo.json");
            }
        };

    }*/

    public View.OnClickListener postTask() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isInserted = db.newTask(new Task(editText.getText().toString(), false, idList));
                if (isInserted)
                    Toast.makeText(TaskActivity.this, "Success !", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(TaskActivity.this, "Error !", Toast.LENGTH_LONG).show();

                //showAllTaskByCurrentListId();
            }
        };
    }

    public void showAllTaskByCurrentListId() {

        List<Task> listTask = new ArrayList<>();
        Cursor data = db.getAllTasksByListId(idList);
        while (data.moveToNext()) {
            listTask.add(new Task(data.getInt(0), data.getString(1), Boolean.getBoolean(data.getString(2)), data.getInt(3)));
        }
        for (Task task : listTask) {
            LinearLayout linearLayout = findViewById(R.id.linearLayout2);
            LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
            linearLayout1.setOrientation(LinearLayout.HORIZONTAL);


            TextView textView = new TextView(this);
            textView.setText(task.getText());
            textView.setTextSize(14);
            textView.setPadding(15, 10, 5, 10);
            textView.setTextColor(Color.WHITE);
            textView.setMaxHeight(600);
            textView.setMaxWidth(600);

            if (task.getIsDone()) {
                linearLayout1.setBackgroundColor(Color.BLACK);
            }
            CheckBox checkBox = new CheckBox(getApplicationContext());
            checkBox.setChecked(task.getIsDone());
            //checkBox.setOnClickListener(modifyTask(this, getApplicationContext(), task, fileName, linearLayout1));

            Button deleteButton = new Button(getApplicationContext());
            deleteButton.setText("X");
            deleteButton.setPadding(2, 2, 2, 2);

            //deleteButton.setOnClickListener(configureDeleteButton(activity, context, task, fileName, linearLayout1));


            linearLayout1.addView(checkBox);
            linearLayout1.addView(textView);
            linearLayout1.addView(deleteButton);
            linearLayout.addView(linearLayout1);
        }
    }
}

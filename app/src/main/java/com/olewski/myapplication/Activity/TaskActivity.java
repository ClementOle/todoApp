package com.olewski.myapplication.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.olewski.myapplication.R;
import com.olewski.myapplication.Util.UtilFilesStorage;
import com.olewski.myapplication.model.Task;
import com.olewski.myapplication.service.TaskService;

import java.util.List;


public class TaskActivity extends AppCompatActivity {
    public static Integer idList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        UtilFilesStorage.createFile(this, "dataTodo.json");

        List<Task> tasks = TaskService.getTaskFromJson(getApplicationContext(), TaskActivity.this, "dataTodo.json");
        if(tasks != null && tasks.size() > 0) {
            for (Task task : tasks) {
                if (task.getListId().equals(TaskActivity.idList))
                    TaskService.showTask(TaskActivity.this, getApplicationContext(), task, "dataTodo.json");
            }
        }

        Button postButton = findViewById(R.id.button2);
        postButton.setOnClickListener(configurePostButton());


    }


    protected View.OnClickListener configurePostButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskService.saveTaskInJson(getApplicationContext(), TaskActivity.this, "dataTodo.json");
                //TaskService.getTaskFromJson(getApplicationContext(), TaskActivity.this, "dataTodo.json");
            }
        };

    }
}

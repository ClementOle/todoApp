package com.olewski.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.olewski.myapplication.R;
import com.olewski.myapplication.Util.UtilFilesStorage;
import com.olewski.myapplication.service.TaskService;


public class TaskActivity extends AppCompatActivity {
    public static Integer idList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        UtilFilesStorage.createFile(this, "dataTodo.json");
        get();

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                get();
            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                post();
                get();
            }
        });

        Button button1 = findViewById(R.id.button4);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMenu();

            }
        });
    }

    public void gotoMenu() {
        finish();
    }

    protected void get() {
        TaskService.getTaskFromJson(this.getApplicationContext(), this, "dataTodo.json");
    }

    protected void post() {
        TaskService.saveTaskInJson(this.getApplicationContext(), this, "dataTodo.json");
    }
}

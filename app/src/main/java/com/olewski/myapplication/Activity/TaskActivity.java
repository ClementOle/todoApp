package com.olewski.myapplication.Activity;

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

        TaskService.getTaskFromJson(getApplicationContext(), TaskActivity.this, "dataTodo.json");

        Button postButton = findViewById(R.id.button);
        postButton.setOnClickListener(configurePostButton());

        Button getButton = findViewById(R.id.button2);
        getButton.setOnClickListener(configureGetButton());

        /*Button button1 = findViewById(R.id.button4);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMenu();

            }
        });*/
    }

    /*public void gotoMenu() {
        finish();
    }*/

    protected View.OnClickListener configureGetButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskService.getTaskFromJson(getApplicationContext(), TaskActivity.this, "dataTodo.json");
            }
        };
    }

    protected View.OnClickListener configurePostButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskService.saveTaskInJson(getApplicationContext(), TaskActivity.this, "dataTodo.json");
                TaskService.getTaskFromJson(getApplicationContext(), TaskActivity.this, "dataTodo.json");
            }
        };

    }
}

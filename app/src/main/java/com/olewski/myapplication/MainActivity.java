package com.olewski.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.olewski.myapplication.Util.UtilFilesStorage;
import com.olewski.myapplication.model.Task;
import com.olewski.myapplication.service.TaskService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UtilFilesStorage.createFile(this);
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
    }

    protected void get() {
        new TaskService().getJson(this.getApplicationContext(), this);
//        new TaskService().getRequest(this.getApplicationContext(), this);
    }

    protected void post() {
        new TaskService().saveJson(this.getApplicationContext(), this);
        //new TaskService().postRequest(this.getApplicationContext(), this);
    }
}

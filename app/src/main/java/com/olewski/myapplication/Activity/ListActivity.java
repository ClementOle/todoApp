package com.olewski.myapplication.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.olewski.myapplication.Activity.TaskActivity;
import com.olewski.myapplication.R;
import com.olewski.myapplication.Util.UtilFilesStorage;
import com.olewski.myapplication.model.List;
import com.olewski.myapplication.service.ListService;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Button buttonAAa = findViewById(R.id.button3);
        buttonAAa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("click");
                gotoMainActivity();
            }
        });

        Button buttonB = findViewById(R.id.button5);

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

//        UtilFilesStorage.createFile(this, "dataList.json");
//        List list = new List("todo");

        LinearLayout linearLayout = findViewById(R.id.listTaskLinearLayout);

        ListService.getListFromJson(this.getApplicationContext(), this, "dataList.json");

    }

    public void save() {
        ListService.saveListInJson(getApplicationContext(), this, "dataList.json");
    }

    public void gotoMainActivity() {
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }




}

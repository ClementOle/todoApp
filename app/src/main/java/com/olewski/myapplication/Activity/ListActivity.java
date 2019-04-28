package com.olewski.myapplication.Activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.olewski.myapplication.R;
import com.olewski.myapplication.model.List;
import com.olewski.myapplication.service.ListService;

import java.util.EventListener;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Button buttonB = findViewById(R.id.button5);

        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });


        ListService.getListFromJson(this.getApplicationContext(), this, "dataList.json");

    }

    public void save() {
        ListService.saveListInJson(getApplicationContext(), this, "dataList.json");
        ListService.getListFromJson(getApplicationContext(), this, "dataList.json");
    }

    public void gotoMainActivity() {
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }

    public void gotoTaskActivity(int listId, Context context) {
        System.out.println(listId);
        TaskActivity.idList = listId;
        Intent intent = new Intent(context, TaskActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
    public View.OnClickListener aa(final Context context, final List list) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,
                        "Open !", Toast.LENGTH_LONG).show();
               gotoTaskActivity(list.getId(), context);
            }
        };
    }

}

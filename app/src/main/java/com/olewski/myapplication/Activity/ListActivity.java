package com.olewski.myapplication.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.olewski.myapplication.Activity.TaskActivity;
import com.olewski.myapplication.R;

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

        LinearLayout linearLayout = findViewById(R.id.listTaskLinearLayout);



    }

    public void gotoMainActivity() {
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }




}

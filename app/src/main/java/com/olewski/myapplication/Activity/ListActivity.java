package com.olewski.myapplication.Activity;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.olewski.myapplication.R;
import com.olewski.myapplication.SQLiteDataBaseHelper;
import com.olewski.myapplication.model.List;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    SQLiteDataBaseHelper db;
    EditText listEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        Button listPostButton = findViewById(R.id.listPostButton);

        listEditText = findViewById(R.id.listEditText);

        db = new SQLiteDataBaseHelper(this);


        listPostButton.setOnClickListener(test());
        showAll();
    }


    public View.OnClickListener test() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isInserted = db.newList(listEditText.getText().toString());
                if (isInserted)
                    Toast.makeText(ListActivity.this, "Success !", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ListActivity.this, "Error !", Toast.LENGTH_LONG).show();

                showAll();
            }
        };
    }

    public void showAll() {
        LinearLayout linearLayout = findViewById(R.id.listTaskLinearLayout);
        linearLayout.removeAllViews();
        Cursor data = db.getAllList();
        java.util.List<List> listAllList = new ArrayList<>();
        while (data.moveToNext()) {
            listAllList.add(new List(data.getString(0)));
            listAllList.add(new List(data.getString(1)));
        }

        for (List list : listAllList) {
            LinearLayout linearLayout1 = new LinearLayout(getApplicationContext());
            linearLayout1.setOrientation(LinearLayout.HORIZONTAL);

            Button button = new Button(getApplicationContext());
            button.setText(list.getName());
            button.setPadding(2, 2, 2, 2);
            button.setId(list.getId());
            //button.setOnClickListener(new ListActivity().openList(context, list));

            linearLayout1.addView(button);
            linearLayout.addView(linearLayout1);
        }
    }
}

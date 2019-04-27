package com.olewski.myapplication.service;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.olewski.myapplication.R;
import com.olewski.myapplication.Util.UtilFilesStorage;
import com.olewski.myapplication.model.List;
import com.olewski.myapplication.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ListService {

    public static void getListFromJson(final Context context, final Activity activity, String fileName) {
        try {
            JSONArray jsonArray = UtilFilesStorage.readDataToJson(context, fileName);
            if (jsonArray != null) {
                LinearLayout linearLayout = activity.findViewById(R.id.listTaskLinearLayout);
                linearLayout.removeAllViews();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    List list = null;
                    if (jsonObject.has("name"))
                        list = new List(jsonObject.getString("name"));
                    if (list != null)
                        showList(activity, context, list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void saveListInJson(Context context, Activity activity, String fileName) {
        try {
            EditText editText = activity.findViewById(R.id.textViewNewList);

            List list = new List(editText.getText().toString());
            editText.setText("");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", list.getId());
            jsonObject.put("name", list.getName());

            UtilFilesStorage.writeDataInJson(context, jsonObject, fileName);

        } catch (Exception e) {
            System.out.println("It didn't work !! \n" + e.toString());
        }
    }

    public static void showList(Activity activity, final Context context, List list) {
        LinearLayout linearLayout = activity.findViewById(R.id.listTaskLinearLayout);
        LinearLayout linearLayout1 = new LinearLayout(context);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);


        TextView textView = new TextView(activity);
        textView.setText(list.getName());
        textView.setTextSize(14);
        textView.setPadding(15, 10, 5, 10);
        textView.setTextColor(Color.WHITE);
        textView.setMaxHeight(600);
        textView.setMaxWidth(600);


        Button button = new Button(context);
        button.setText("✓");
        button.setPadding(2, 2, 2, 2);
        button.setId(list.getId());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,
                        "Open !", Toast.LENGTH_LONG).show();

            }
        });


        linearLayout1.addView(textView);
        linearLayout1.addView(button);
        linearLayout.addView(linearLayout1);
    }
}

package com.olewski.myapplication.service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.olewski.myapplication.Activity.ListActivity;
import com.olewski.myapplication.Activity.TaskActivity;
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
                    if(jsonObject.has("id") && jsonObject.has("name"))
                        list = new List(jsonObject.getInt("id"),jsonObject.getString("name"));
                    else if (jsonObject.has("name"))
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
            EditText editText = activity.findViewById(R.id.editText2);

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

    public static void showList(final Activity activity, final Context context, final List list) {
        LinearLayout linearLayout = activity.findViewById(R.id.listTaskLinearLayout);
        LinearLayout linearLayout1 = new LinearLayout(context);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);

        Button button = new Button(context);
        button.setText(list.getName());
        button.setPadding(2, 2, 2, 2);
        button.setId(list.getId());
        button.setOnClickListener(new ListActivity().aa(context, list));


        linearLayout1.addView(button);
        linearLayout.addView(linearLayout1);
    }


}

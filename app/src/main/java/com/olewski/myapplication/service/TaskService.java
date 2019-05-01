package com.olewski.myapplication.service;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.olewski.myapplication.Activity.TaskActivity;
import com.olewski.myapplication.R;
import com.olewski.myapplication.Util.UtilFilesStorage;
import com.olewski.myapplication.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TaskService {

    public static void getTaskFromJson(final Context context, final Activity activity, String fileName) {
        try {
            JSONArray jsonArray = UtilFilesStorage.readDataToJson(context, fileName);
            if (jsonArray != null) {
                LinearLayout linearLayout = activity.findViewById(R.id.linearLayout2);
                linearLayout.removeAllViews();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.has("id") && jsonObject.has("text") && jsonObject.has("isDone")) {
                        if (jsonObject.has("listId") && TaskActivity.idList == jsonObject.getInt("listId")) {
                            Task task = new Task(jsonObject.getDouble("id"), jsonObject.getString("text"), jsonObject.getBoolean("isDone")
                                    , jsonObject.getInt("listId"));
                            showTask(activity, context, task, fileName);
                        } else if (!jsonObject.has("listId") && TaskActivity.idList == 0) {
                            Task task = new Task(jsonObject.getDouble("id"), jsonObject.getString("text"), jsonObject.getBoolean("isDone"));
                            showTask(activity, context, task, fileName);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void saveTaskInJson(Context context, Activity activity, String fileName) {
        try {
            EditText editText = activity.findViewById(R.id.editText);

            Task task = new Task(editText.getText().toString(), false);
            editText.setText("");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", Math.random() * 2000);
            jsonObject.put("text", task.getText());
            jsonObject.put("isDone", false);
            jsonObject.put("listId", TaskActivity.idList);

            UtilFilesStorage.writeDataInJson(context, jsonObject, null, fileName);

        } catch (Exception e) {
            System.out.println("It didn't work !! \n" + e.toString());
        }
    }

    private static void showTask(final Activity activity, final Context context, final Task task, final String fileName) {
        LinearLayout linearLayout = activity.findViewById(R.id.linearLayout2);
        LinearLayout linearLayout1 = new LinearLayout(context);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);


        TextView textView = new TextView(activity);
        textView.setText(task.getText());
        textView.setTextSize(14);
        textView.setPadding(15, 10, 5, 10);
        textView.setTextColor(Color.WHITE);
        textView.setMaxHeight(600);
        textView.setMaxWidth(600);

        if (task.getDone()) {
            linearLayout1.setBackgroundColor(Color.BLACK);
        }
        CheckBox checkBox = new CheckBox(context);
        checkBox.setChecked(task.getDone());
        checkBox.setOnClickListener(modifyTask(activity, context, task, fileName));

        Button deleteButton = new Button(context);
        deleteButton.setText("X");
        deleteButton.setPadding(2, 2, 2, 2);
//        deleteButton.setId(task.getId());
        deleteButton.setOnClickListener(configureDeleteButton(activity, context, task, fileName));

        linearLayout1.addView(checkBox);
        linearLayout1.addView(textView);
        linearLayout1.addView(deleteButton);
        linearLayout.addView(linearLayout1);
    }

    private static View.OnClickListener modifyTask(final Activity activity, final Context context, final Task task, final String fileName) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject jsonObjectToModify = new JSONObject();
                    jsonObjectToModify.put("id", task.getId());
                    jsonObjectToModify.put("text", task.getText());
                    jsonObjectToModify.put("isDone", task.getDone());
                    if (task.getListId() != null)
                        jsonObjectToModify.put("listId", task.getListId());

                    UtilFilesStorage.modifyDataInJson(context, jsonObjectToModify, fileName);
                    getTaskFromJson(context, activity, fileName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

    }

    private static View.OnClickListener configureDeleteButton(final Activity activity, final Context context, final Task task, final String fileName) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Conversion de l'object en jsonObject
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", task.getId());
                    jsonObject.put("text", task.getText());
                    jsonObject.put("isDone", task.getDone());
                    if (task.getListId() != null)
                        jsonObject.put("listId", task.getListId());

                    UtilFilesStorage.removeDataInJson(context, jsonObject, fileName);
                    getTaskFromJson(context, activity, fileName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}

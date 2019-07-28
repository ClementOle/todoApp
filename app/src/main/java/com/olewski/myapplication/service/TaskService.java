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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.olewski.myapplication.Activity.TaskActivity;
import com.olewski.myapplication.R;
import com.olewski.myapplication.Util.UtilFilesStorage;
import com.olewski.myapplication.model.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TaskService {

    public static List<Task> getTaskFromJson(final Context context, final Activity activity, String fileName) {
        try {
            //Ouverture du fichier
            FileInputStream file = UtilFilesStorage.openFileToRead(context, fileName);
            StringBuilder data = new StringBuilder();
            List<Byte> byteArray = new ArrayList<>();
            if (file != null) {
                int bytes;
                //Récupération de chaque byte du fichier
                while ((bytes = file.read()) != -1)
                    byteArray.add((byte) bytes);

                //Conversion de la liste de bytes en tableau de byte
                byte[] a = new byte[byteArray.size()];
                for (int i = 0; i < byteArray.size(); i++) {
                    a[i] = byteArray.get(i);
                }
                //Conversion du tableau de byte en utf-8
                data.append(new String(a, StandardCharsets.UTF_8));
                //Fermeture du fichier
                UtilFilesStorage.closeFileToRead(file);
            }

            ObjectMapper mapper = new ObjectMapper();

            //Transforme le texte json en objet
            return Arrays.asList(mapper.readValue(data.toString(), Task[].class));


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void saveTaskInJson(Context context, Activity activity, String fileName) {
        try {
            EditText editText = activity.findViewById(R.id.editText);

            ObjectMapper mapper = new ObjectMapper();

            List<Task> currentTasks = getTaskFromJson(context, activity, fileName);
            if (currentTasks != null) {
                Task newTask = new Task(null, editText.getText().toString(), false, TaskActivity.idList);
                List<Task> listTasksModif = new ArrayList<>(currentTasks);
                listTasksModif.add(newTask);
                mapper.writerWithDefaultPrettyPrinter().writeValue(UtilFilesStorage.openFileToWrite(context, fileName), listTasksModif);
                for (Task task : listTasksModif) {
                    showTask(activity, context, task, fileName);
                }
            } else {
                Task[] newTask = new Task[1];
                newTask[0] = new Task(null, editText.getText().toString(), false, TaskActivity.idList);
                mapper.writerWithDefaultPrettyPrinter().writeValue(UtilFilesStorage.openFileToWrite(context, fileName), newTask);
                showTask(activity, context, newTask[0], fileName);
            }

            editText.setText("");

        } catch (Exception e) {
            System.out.println("It didn't work !! \n" + e.toString());
        }
    }

    public static void showTask(final Activity activity, final Context context, final Task task, final String fileName) {
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

        if (task.getIsDone()) {
            linearLayout1.setBackgroundColor(Color.BLACK);
        }
        CheckBox checkBox = new CheckBox(context);
        checkBox.setChecked(task.getIsDone());
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
                    jsonObjectToModify.put("isDone", task.getIsDone());
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
                    jsonObject.put("isDone", task.getIsDone());
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

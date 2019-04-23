package com.olewski.myapplication.service;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.util.JsonWriter;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.olewski.myapplication.R;
import com.olewski.myapplication.Util.UtilFilesStorage;
import com.olewski.myapplication.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.List;

public class TaskService {


    public TaskService() {
    }

    public void postRequest(final Context context, final Activity activity) {

        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://192.168.43.60:8080/tasks";


        EditText editText = activity.findViewById(R.id.editText);
        Task task = new Task(editText.getText().toString(), false);
        editText.setText("");
        JSONObject jsonTask = null;
        try {
            jsonTask = new JSONObject("{ 'text' : '" + task.getText() + "' , 'isDone' : 'false' }");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonTask, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonTask = response;
                    Task task = null;
                    task = new Task(Integer.parseInt(jsonTask.getString("id")), jsonTask.getString("text"), Boolean.parseBoolean(jsonTask.getString("isDone")));

                    getRequest(context, activity);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work !!! \n" + error.toString());
            }
        });
        queue.add(jsonObjectRequest);
    }

    public void getJson(final Context context, final Activity activity) {
        try {
            JSONArray jsonArray = UtilFilesStorage.readDataToJson(context);
            if (jsonArray != null) {
                LinearLayout linearLayout = activity.findViewById(R.id.linearLayout2);
                linearLayout.removeAllViews();
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Task task = new Task(jsonObject.getInt("id"), jsonObject.getString("text"), jsonObject.getBoolean("isDone"));
                    showTask(activity, context, task);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void saveJson(Context context, Activity activity) {
        try {
            EditText editText = activity.findViewById(R.id.editText);

            Task task = new Task(editText.getText().toString(), false);
            editText.setText("");
            //Echappement de toutes les apostrophe potentiellement présente dans le texte
            String text = task.getText();
            while(text.contains("'") && ((text.indexOf("'") != text.indexOf("\\")+1) || text.indexOf("'") == 0))
                    text = text.replace("'", "\\'");


            JSONObject jsonObject = new JSONObject("{ 'id' : '" + Math.random() * 2000 + "', 'text' : '" + text + "' , 'isDone' : 'false' }");

            UtilFilesStorage.writeDataInJson(context, jsonObject);

        } catch (Exception e) {
            System.out.println("It didn't work !! \n" + e.toString());
        }
    }

   /* public void deleteTaskInFile(Context context, Activity activity, int id) {
        try {
            JSONArray jsonArray = UtilFilesStorage.readDataToJson(context);
            boolean find = false;
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getInt("id") == id) {
                        find = true;
                        break;
                    }
                }
            }
            JSONArray jsonArray1 = UtilFilesStorage.writeDataInJson(this, )
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public void getRequest(final Context context, final Activity activity) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://192.168.43.60:8080/tasks";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    // Loop through the array elements
                    ViewGroup viewGroup = activity.findViewById(R.id.linearLayout2);
                    for (int index = 0; index < ((ViewGroup) viewGroup).getChildCount(); ++index) {
                        View nextChild = ((ViewGroup) viewGroup).getChildAt(index);
                        nextChild.setVisibility(View.GONE);
                    }
                    System.out.println("Flush");
                    LinearLayout linearLayout = activity.findViewById(R.id.linearLayout2);
                    linearLayout.removeAllViews();
                    for (int i = 0; i < response.length(); i++) {
                        // Get current json object
                        JSONObject jsonTask = response.getJSONObject(i);
                        Task task = new Task(Integer.parseInt(jsonTask.getString("id")), jsonTask.getString("text"), Boolean.parseBoolean(jsonTask.getString("isDone")));

                        showTask(activity, context, task);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work !! \n" + error.toString());
            }
        });
        queue.add(jsonArrayRequest);
    }


    private void delete(final Task task, final Activity activity, final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://192.168.43.60:8080/tasks/" + task.getId();
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getRequest(context, activity);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("That didn't work !! \n" + error.toString());
            }
        });
        queue.add(stringRequest);
    }

    private void update(final Task task, final Activity activity, final Context context) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://192.168.43.60:8080/tasks/";
        try {
            JSONObject jsonObject = new JSONObject("{ 'id' : '" + task.getId() + "', 'text' : '" + task.getText() + "', 'isDone' : '" + task.getDone() + "'}");

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    getRequest(context, activity);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("That didn't work !! \n" + error.toString());
                }
            });
            queue.add(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showTask(final Activity activity, final Context context, final Task task) {
        LinearLayout linearLayout = activity.findViewById(R.id.linearLayout2);
        LinearLayout linearLayout1 = new LinearLayout(context);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);


        TextView textView = new TextView(activity);
        textView.setText(task.getText());
        textView.setTextSize(14);
        textView.setPadding(15, 5, 5, 5);
        textView.setMaxWidth(600);
/*
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int screenWidth = size.x;
        int screenHeight = size.y;
*/


        if (task.getDone()) {
            linearLayout1.setBackgroundColor(Color.BLACK);
        }

        Button button = new Button(context);
        button.setText("✓");
        button.setPadding(2, 2, 2, 2);
        button.setId(task.getId());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setDone(!task.getDone());
//                update(task, activity, context);

            }
        });

        Button button2 = new Button(context);
        button2.setText("X");
        button2.setPadding(2, 2, 2, 2);
        button2.setId(task.getId());
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                delete(task, activity, context);
                try {
                    String taskInJson = task.toStringJson();
                    UtilFilesStorage.removeDataInJson(context, new JSONObject(taskInJson));
                    getJson(context, activity);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        linearLayout1.addView(textView);
        linearLayout1.addView(button);
        linearLayout1.addView(button2);
        linearLayout.addView(linearLayout1);
    }


}

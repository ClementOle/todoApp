package com.olewski.myapplication.service;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.olewski.myapplication.R;
import com.olewski.myapplication.model.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                    TextView textView = activity.findViewById(R.id.textView2);
                    // Display the formatted json data in text view
                    showTask(activity, context, task.getText());

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

    public void getRequest(final Context context, final Activity activity) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://192.168.43.60:8080/tasks";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    TextView textView = activity.findViewById(R.id.textView2);
                    textView.setText("");
                    // Loop through the array elements
                    for (int i = 0; i < response.length(); i++) {
                        // Get current json object
                        JSONObject jsonTask = response.getJSONObject(i);
                        Task task = new Task(Integer.parseInt(jsonTask.getString("id")), jsonTask.getString("text"), Boolean.parseBoolean(jsonTask.getString("isDone")));

                        showTask(activity, context, task.getText());
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

    private void showTask(Activity activity, Context context, String text) {
        LinearLayout linearLayout = activity.findViewById(R.id.linearLayout);

        TextView TextView = new TextView(activity);
        TextView.setText(text);
        TextView.setTextSize(14);
        TextView.setPadding(5, 5, 5, 5);

        linearLayout.addView(TextView);
    }

}

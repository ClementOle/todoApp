package com.olewski.myapplication.service;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.android.volley.toolbox.StringRequest;
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
        System.out.println(url);
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

    private void showTask(final Activity activity, final Context context, final Task task) {
        LinearLayout linearLayout = activity.findViewById(R.id.linearLayout2);
        LinearLayout linearLayout1 = new LinearLayout(context);
        linearLayout1.setOrientation(LinearLayout.HORIZONTAL);


        TextView textView = new TextView(activity);
        textView.setText(task.getText());
        textView.setTextSize(14);
        textView.setPadding(15, 5, 5, 5);
        Button button = new Button(context);
        button.setText("✓");
        button.setPadding(5, 5, 5, 5);
        button.setId(task.getId());

        Button button2 = new Button(context);
        button2.setText("X");
        button2.setPadding(5, 5, 5, 5);
        button2.setId(task.getId());
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(task, activity, context);
            }
        });


        linearLayout1.addView(textView);
        linearLayout1.addView(button);
        linearLayout1.addView(button2);
        linearLayout.addView(linearLayout1);
    }


}

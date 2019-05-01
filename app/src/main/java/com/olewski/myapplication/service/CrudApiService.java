package com.olewski.myapplication.service;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

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

public class CrudApiService {
    /**
     * @param context
     * @param activity
     */
    public static void postRequest(final Context context, final Activity activity) {

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
                    Task task = new Task(jsonTask.getDouble("id"), jsonTask.getString("text"), jsonTask.getBoolean("isDone"));

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

    /**
     * @param context
     * @param activity
     */
    public static void getRequest(final Context context, final Activity activity) {
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
                        Task task = new Task(jsonTask.getDouble("id"), jsonTask.getString("text"), jsonTask.getBoolean("isDone"));

                        //showTask(activity, context, task);
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

    /**
     * @param task
     * @param activity
     * @param context
     */
    private static void delete(final Task task, final Activity activity, final Context context) {
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

    /**
     * @param task
     * @param activity
     * @param context
     */
    private static void update(final Task task, final Activity activity, final Context context) {
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
}

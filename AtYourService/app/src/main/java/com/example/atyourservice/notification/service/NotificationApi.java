package com.example.atyourservice.notification.service;

import android.content.Context;
import android.os.StrictMode;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//https://firebase.google.com/docs/cloud-messaging/android/client
public class NotificationApi {
    private static String BASE_URL="https://fcm.googleapis.com/fcm/send";
    private String TOKEN;
    private final static String SERVER_TOKEN="key=AAAA5b3ChMU:APA91bHn_Jsr42w1JqzaPxpMEEXC96NbueibBqTe0H6aWVXNl9J2zz2m9O6o_DRRkcCpg4Q1L9jf0H7ySNpJQ-iCsaszkDSAH5D0j8ltL6qBBx0FqdM1yqInfUgRYF5lVRC5mt_3b5nB";

    public NotificationApi(String token) {
        this.TOKEN = token;
    }

    public void pushNotificationToReceiver(Context context, String title, String message, int stickerId) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(context);

        try {
            JSONObject json = new JSONObject();
            System.out.println("TOKEN IS :::: "+TOKEN);
            json.put("to", TOKEN);
            json.put("project_id", "986731152581");
            JSONObject notification = new JSONObject();
            notification.put("title", title);
            notification.put("body", message);
            notification.put("icon", String.valueOf(stickerId));

            json.put("notification", notification);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    System.out.println("Response : " + response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    params.put("Authorization", SERVER_TOKEN);
                    params.put("Accept", "application/json");
                    return params;
                }
            };
            queue.add(jsonObjectRequest);
        } catch(JSONException je) {
            je.printStackTrace();
        }
    }
}

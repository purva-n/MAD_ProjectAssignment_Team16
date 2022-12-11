package com.example.atyourservice.togather.Notifications;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//https://firebase.google.com/docs/cloud-messaging/android/client
public class ToGatherNotificationApi {
    private static String BASE_URL="https://fcm.googleapis.com/fcm/send";
    private String TOKEN;
    ArrayList<String> groupUserTokens;
    private final static String SERVER_TOKEN="key=AAAAywhhAIg:APA91bG6axWjPhdC00kzGS-C19-7xJr1D-Sx9xYHOIktXwiklkp-DQ_sCyAl-ryKSChcJFC0OMB6Ie-eqFTIDjChOsNKN-osu3wpLdVJifNda5hM-Ac0-zCDVf_9fzh1kiHCgi09A4WL";

    public ToGatherNotificationApi(ArrayList<String> groupUserTokens) {
        this.groupUserTokens = groupUserTokens;
    }

    public void pushNotificationToReceiver(Context context, String groupid, String message) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(context);

            for (int i = 0; i < groupUserTokens.size(); i++){
                try {
                JSONObject json = new JSONObject();
                System.out.println("TOKEN IS :::: " + TOKEN);
                json.put("to", groupUserTokens.get(i));
                json.put("project_id", "872018935944");//this value is sender id from project settings in firebase console under firebase messaging.
                JSONObject notification = new JSONObject();
                notification.put("title", groupid);
                notification.put("body", message);

                json.put("notification", notification);
                // for notifications
                    // firebaseRef.groupusertokens.get(i).child...notification.push(json);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL, json, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println("Response : " + response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
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
            } catch(JSONException je){
                je.printStackTrace();
            }
        }
    }
}

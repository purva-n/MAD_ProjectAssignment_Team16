package com.example.atyourservice;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.atyourservice.api.response.pojo.Embedded;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;


public class EventSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_search);
    }

    private void searchEvents(String url) {
        RequestQueue volleyQueue = Volley.newRequestQueue(EventSearchActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,

                (Response.Listener<JSONObject>) response -> {
                    String responseJSON;
                    try {
                        responseJSON = response.getJSONObject("_embedded").toString();

                        ObjectMapper mapper = new ObjectMapper();
                        Embedded responsePojo = mapper.readValue(responseJSON, Embedded.class);

                        Intent intent = new Intent(this, ActivityRecyclerView.class);
                        intent.putExtra("ApiResponse", responsePojo);
                        startActivity(intent);

                    }  catch (JsonMappingException e) {
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },

                (Response.ErrorListener) error -> {
                    Toast.makeText(EventSearchActivity.this, "Some error occurred! Cannot fetch dog image", Toast.LENGTH_LONG).show();
                    Log.e("MainActivity", "loadDogImage error: ${error.localizedMessage}");
                }
        );

        volleyQueue.add(jsonObjectRequest);
    }
}
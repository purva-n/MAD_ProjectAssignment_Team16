package com.example.atyourservice;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.atyourservice.api.response.pojo.Embedded;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

public class EventSearchActivity extends AppCompatActivity {
    EditText cityInput, stateInput, zipInput;
    SeekBar radius;
    Switch uom;
    Button searchBut;
    StringBuilder requestURL;
    final String eventsSearchURL = "https://app.ticketmaster.com/discovery/v2/events.json?" +
                                    "apikey=Ho0dw0KonGAOwHbJ3e88wapOXC7t91I8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_search);

        cityInput = (EditText) findViewById(R.id.cityInput);
        stateInput = (EditText) findViewById(R.id.stateInput);
        zipInput = (EditText) findViewById(R.id.zipCodeInput);
        searchBut = (Button) findViewById(R.id.searchButton);
        radius = (SeekBar) findViewById(R.id.seekBar_radius_distance);
        uom = (Switch) findViewById(R.id.uom);

        uom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(uom.getText().toString().equals("miles"))
                    uom.setText("km");
                else {
                    uom.setText("miles");
                }
            }
        });

        requestURL = new StringBuilder();
        requestURL.append(eventsSearchURL);
        System.out.println("zipempty " + zipInput.getText().toString().isEmpty());
        System.out.println("zip Is  num " + IsNumber(zipInput.getText().toString()));

        if(!zipInput.getText().toString().isEmpty() && IsNumber(zipInput.getText().toString())) {
            System.out.println("zip");
            requestURL.append("&postalCode=").append(zipInput.getText().toString());
        } else {
            System.out.println("citystate");

            System.out.println("cityempty " + cityInput.getText().toString().isEmpty());
            System.out.println("city Is  num " + IsNumber(cityInput.getText().toString()));

            System.out.println("stateempty " + stateInput.getText().toString().isEmpty());
            System.out.println("state Is  num " + IsNumber(stateInput.getText().toString()));

            if(!cityInput.getText().toString().isEmpty() && !IsNumber(cityInput.getText().toString())) {
                System.out.println("city");
                requestURL.append("&city=").append(cityInput.getText().toString());
            }
            if(!stateInput.getText().toString().isEmpty() && !IsNumber(stateInput.getText().toString())) {
                System.out.println("state");
                requestURL.append("&stateCode=").append(stateInput.getText().toString());
            }
        }

        requestURL.append("&unit=").append(uom.getText().toString());
        requestURL.append("&radius=").append(radius.getProgress());

        searchBut.setOnClickListener(view -> {
            searchEvents(requestURL.toString());
        });
    }

    private boolean IsNumber(String inp) {
        try {
            Integer.parseInt(inp);
            return true;
        } catch (Exception ex) {
            return  false;
        }
    }

    private void searchEvents(String url) {
        System.out.println("URL "+ url);
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

                    } catch (JsonMappingException e) {
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
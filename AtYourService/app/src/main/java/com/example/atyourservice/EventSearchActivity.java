package com.example.atyourservice;


import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.fasterxml.jackson.databind.node.POJONode;

import org.json.JSONException;
import org.json.JSONObject;

public class EventSearchActivity extends AppCompatActivity {
    EditText cityInput, stateInput, zipInput;
    SeekBar radius;
    Switch uom;
    Button searchBut;
    ProgressBar progressBar;
    StringBuilder requestURL;
    final String eventsSearchURL = "https://app.ticketmaster.com/discovery/v2/events.json?" +
            "apikey=Ho0dw0KonGAOwHbJ3e88wapOXC7t91I8";
    boolean invalid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_search);
        progressBar = findViewById(R.id.progressBar3);
        cityInput = (EditText) findViewById(R.id.cityInput);
        stateInput = (EditText) findViewById(R.id.stateInput);
        zipInput = (EditText) findViewById(R.id.zipCodeInput);
        searchBut = (Button) findViewById(R.id.searchButton);

        sliderListener sldListener = new sliderListener();
        radius = (SeekBar) findViewById(R.id.seekBar_radius_distance);
        radius.setMax(50);
        radius.setProgress(5);
        radius.setOnSeekBarChangeListener(sldListener);

        uom = (Switch) findViewById(R.id.uom);

        uom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (uom.getText().toString().equals("miles"))
                    uom.setText("km");
                else {
                    uom.setText("miles");
                }
            }
        });

        requestURL = new StringBuilder();
        requestURL.append(eventsSearchURL);

        searchBut.setOnClickListener(view -> {
            boolean zipValid = false;
            boolean cityValid = true;
            boolean stateValid = true;
            if (!zipInput.getText().toString().isEmpty() && (zipValid = IsNumber(zipInput.getText().toString()))) {
                requestURL.append("&postalCode=").append(zipInput.getText().toString());

                requestURL.append("&unit=").append(uom.getText().toString());
                requestURL.append("&radius=").append(radius.getProgress());
                zipValid = true;
            } else {
                if (!cityInput.getText().toString().isEmpty() && (cityValid = !IsNumber(cityInput.getText().toString()))) {
                    requestURL.append("&city=").append(cityInput.getText().toString());
                    cityValid = true;
                }
                if (!stateInput.getText().toString().isEmpty() && (stateValid = !IsNumber(stateInput.getText().toString()))) {
                    requestURL.append("&stateCode=").append(stateInput.getText().toString());
                    stateValid = true;
                }

                requestURL.append("&unit=").append(uom.getText().toString());
                requestURL.append("&radius=").append(radius.getProgress());
            }

            if (zipValid || (cityValid && stateValid)) {
                searchEvents(requestURL.toString());
            } else {
                Toast.makeText(EventSearchActivity.this, "Invalid Input. Please enter valid zip or state and city ", Toast.LENGTH_LONG).show();
            }
            zipInput.setText("");
            cityInput.setText("");
            stateInput.setText("");
            radius.setProgress(0);
            requestURL = new StringBuilder(eventsSearchURL);

        });

    }

    private boolean IsNumber(String inp) {
        try {
            Integer.parseInt(inp);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private void searchEvents(String url) {
        progressBar.setVisibility(View.VISIBLE);
        RequestQueue volleyQueue = Volley.newRequestQueue(EventSearchActivity.this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,

                (Response.Listener<JSONObject>) response -> {
                    String responseJSON;
                    try {
                        responseJSON = response.getJSONObject("_embedded").toString();

                        if (!responseJSON.isEmpty()) {
                            ObjectMapper mapper = new ObjectMapper();
                            Embedded responsePojo = mapper.readValue(responseJSON, Embedded.class);
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(this, ActivityRecyclerView.class);
                            intent.putExtra("ApiResponse", responsePojo);
                            startActivity(intent);
                        } else {
                            Toast.makeText(EventSearchActivity.this, "No events in this area :(", Toast.LENGTH_LONG).show();
                        }

                    } catch (JsonMappingException e) {
                        Toast.makeText(EventSearchActivity.this, "No events in this area :(", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    } catch (JsonProcessingException e) {
                        Toast.makeText(EventSearchActivity.this, "No events in this area :(", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    } catch (JSONException e) {
                        Toast.makeText(EventSearchActivity.this, "No events in this area :(", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    } catch (Exception e) {
                        Toast.makeText(EventSearchActivity.this, "No events in this area :(", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                },

                (Response.ErrorListener) error -> {
                    Toast.makeText(EventSearchActivity.this, "Some error occurred! Cannot fetch events", Toast.LENGTH_LONG).show();
                    Log.e("MainActivity", "loadDogImage error: ${error.localizedMessage}");
                }
        );

        volleyQueue.add(jsonObjectRequest);
    }

    private class sliderListener implements SeekBar.OnSeekBarChangeListener {
        private int smoothnessFactor = 2;

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            progress = Math.round(progress / smoothnessFactor);
            TextView lblProgress = (TextView) findViewById(R.id.lblProgress);
            lblProgress.setText(String.valueOf(progress));
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            seekBar.setProgress(Math.round((seekBar.getProgress() + (smoothnessFactor / 2)) / smoothnessFactor) * smoothnessFactor);
        }
    }


}


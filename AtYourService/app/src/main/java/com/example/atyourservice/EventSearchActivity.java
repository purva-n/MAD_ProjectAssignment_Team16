package com.example.atyourservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.atyourservice.api.response.pojo.Dates;
import com.example.atyourservice.api.response.pojo.Embedded;
import com.example.atyourservice.api.response.pojo.Events;
import com.example.atyourservice.api.response.pojo.Images;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class EventSearchActivity extends AppCompatActivity {

    String city, state,zip;

    EditText cityInput, stateInput, zipInput;
    Button searchBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityInput = (EditText) findViewById(R.id.cityInput);
        stateInput = (EditText) findViewById(R.id.stateInput);
        zipInput = (EditText) findViewById(R.id.zipCodeInput);

        final Embedded embedded = new Embedded(EventSearchActivity.this);

        searchBut = (Button) findViewById(R.id.searchButton);
//        cityIdBut = (Button)findViewById(R.id.cityIdBut);
//
//        cityIdBut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                RequestQueue queue = Volley.newRequestQueue(EventSearchActivity.this);
//                String url = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=pLOeuGq2JL05uEGrZG7DuGWu6sh2OnMz&size=4&page=";
//                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        String cityId = "";
//                        try{
//                            JSONObject cityInfo = response.getJSONObject(0);
//                            cityId = cityInfo.getString("city");
//                        }catch(JSONException e){
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(EventSearchActivity.this,"Something wrong.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
        searchBut.setOnClickListener(new View.OnClickListener() {
            //get user input
            @Override
            public void onClick(View view) {
                city = cityInput.getText().toString();
//                state = stateInput.getText().toString();
                zip = zipInput.getText().toString();

                embedded.getEvents(city,zip, new Embedded.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(EventSearchActivity.this,"Something wrong.",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String city, String zipCode) {
                        Toast.makeText(EventSearchActivity.this,"Found an event in " + city, Toast.LENGTH_SHORT).show();

                    }
                });

//                RequestQueue requestQueue = Volley.newRequestQueue(EventSearchActivity.this);
//                String url = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=pLOeuGq2JL05uEGrZG7DuGWu6sh2OnMz&size=4&page=";
//
//                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,url, null, new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try {
//                            JSONArray firstEvent = response.getJSONArray(0);
//                            Events event = new Events();
//
//                            JSONObject event_from_api = (JSONObject) firstEvent.get(3);
//                            event.setName(event_from_api.getString("name"));
//                            event.setId(event_from_api.getString("id"));
//                            event.setUrl(event_from_api.getString("url"));
//                            event.setType(event_from_api.getString("type"));
//                            event.setImages(event_from_api.getString("images"));
//                            event.setDates(event_from_api.getString("dates"));
////
////                            String id = response.getString(0);
////                            String city = response.getString("city");
////                            String zipCode = response.getString(5);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(EventSearchActivity.this,"Something wrong.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                requestQueue.add(request);
                }
        });
    }
}
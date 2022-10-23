package com.example.atyourservice.api.response.pojo;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Embedded {
    private String QUERY_FOR_CITY = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=pLOeuGq2JL05uEGrZG7DuGWu6sh2OnMz&size=4&page=";
    Context context;
    String name = "";
    String Url = "";
    String type = "";
    List<String> images;
    Dates dates;
    String id = "";

    public Embedded(Context context){
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(String city, String zipCode);
    }

    public List<String> getEvents(String city, String zipCode, VolleyResponseListener volleyResponseListener){
        String url = QUERY_FOR_CITY + city + zipCode;
        List<String> events = new ArrayList<>();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject embedded = response.getJSONObject(3);
                    name = embedded.getString("name");
                    id = embedded.getString("id");
                    type = embedded.getString("type");
                    Url = embedded.getString("url");
                    images = (List) embedded.getJSONArray("images");
                    dates = response.getJSONArray("dates");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyResponseListener.onResponse(city, zipCode);
            }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Toast.makeText(context, "Something wrong.", Toast.LENGTH_SHORT).show();
                    volleyResponseListener.onError("Something wrong.");
                }
        });
        mySingleton.getInstance(context).addToRequestQueue(request);
//        events.addAll(Arrays.asList(name,id,type,Url,images,dates));
        events.add(name);
        events.add(type);
        events.add(id);
        events.add(Url);
        return events;
    }

    public interface SetResponseListener{
        void onError(String message);
        void onResponse(Events event);
    }

    private List<Events> events;


    public void setEvents(List<Events> events, SetResponseListener setResponseListener) {
        String url = QUERY_FOR_CITY + events;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONArray firstEvent = response.getJSONArray(0);
                    Events event = new Events();
                    JSONObject event_from_api = (JSONObject) firstEvent.get(3);

                    event.setName(event_from_api.getString("name"));
                    event.setId(event_from_api.getString("id"));
                    event.setUrl(event_from_api.getString("url"));
                    event.setType(event_from_api.getString("type"));
                    event.setImages(event_from_api.getString("images"));
                    event.setDates(event_from_api.getString("dates"));
                    setResponseListener.onResponse(event);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Something wrong.", Toast.LENGTH_SHORT).show();
                setResponseListener.onError("Something wrong.");
            }
        });
    }
}

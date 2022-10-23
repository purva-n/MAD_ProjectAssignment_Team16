package com.example.atyourservice.api.response.pojo;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class mySingleton {
    private static mySingleton instance;
    private RequestQueue requestQueue;
    private static Context ctx;

    private mySingleton(Context context){
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized mySingleton getInstance(Context context){
        if (instance == null){
            instance = new mySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req){
        getRequestQueue().add(req);
    }
}

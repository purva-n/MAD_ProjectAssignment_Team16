package com.example.atyourservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.atyourservice.api.response.pojo.Embedded;

public class EventSearchActivity extends AppCompatActivity {

    String city, state, zip;

    EditText cityInput, stateInput, zipInput;
    Button searchBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_search);

        cityInput = (EditText) findViewById(R.id.cityInput);
        stateInput = (EditText) findViewById(R.id.stateInput);
        zipInput = (EditText) findViewById(R.id.zipCodeInput);

        final Embedded embedded = new Embedded();

        searchBut = (Button) findViewById(R.id.searchButton);
    }
}
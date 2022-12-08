package com.example.atyourservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.atyourservice.api.response.pojo.Embedded;

import java.util.ArrayList;

public class ActivityRecyclerView extends AppCompatActivity {
    private Embedded linkList;
    RecyclerView linkRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        linkList = (Embedded) getIntent().getSerializableExtra("ApiResponse");

        linkRecycleView = findViewById(R.id.recyclerView);
        linkRecycleView.setLayoutManager(new LinearLayoutManager(ActivityRecyclerView.this));
        linkRecycleView.setAdapter(new EventLinksAdapter(ActivityRecyclerView.this, linkList));
    }
}
package com.example.atyourservice.ChatPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.example.atyourservice.R;
import com.example.atyourservice.models.Message;

import java.util.ArrayList;

    public class MessagesRecycler extends AppCompatActivity {

        RecyclerView messagesRecycleView;
        private ArrayList<Message> msgs;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_recycler_view);
            messagesRecycleView = findViewById(R.id.recyclerView);
            messagesRecycleView.setLayoutManager(new LinearLayoutManager(this));
            messagesRecycleView.setAdapter(new ChatMessageAdapter(MessagesRecycler.this, msgs));


        }
    }



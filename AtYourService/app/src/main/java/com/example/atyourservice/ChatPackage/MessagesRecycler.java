package com.example.atyourservice.ChatPackage;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;


import com.example.atyourservice.R;
import com.example.atyourservice.UserList;
import com.example.atyourservice.api.response.pojo.Messages;

public class MessagesRecycler extends AppCompatActivity {

        RecyclerView messagesRecycleView;
        private Messages msgs;
        private String sender;
        private String receiver;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        msgs = (Messages) getIntent().getSerializableExtra("Messages");
        sender = (String) getIntent().getSerializableExtra("Sender");
        receiver = (String) getIntent().getSerializableExtra("Receiver");

        @DrawableRes int[] stickers = {R.drawable.thumbs_up, R.drawable.thumbs_down, R.drawable.love, R.drawable.celebrate, };
        //https://www.geeksforgeeks.org/gridview-using-baseadapter-in-android-with-example/
        GridAdapter gridAdapter = new GridAdapter(this, stickers, sender, receiver);
        GridView gridView = findViewById(R.id.GridView);
        gridView.setAdapter(gridAdapter);

        messagesRecycleView = findViewById(R.id.messagesRecycler);
        messagesRecycleView.setLayoutManager(new LinearLayoutManager(MessagesRecycler.this));
        messagesRecycleView.setAdapter(new ChatMessageAdapter(MessagesRecycler.this, msgs.getMessages()));
    }
}



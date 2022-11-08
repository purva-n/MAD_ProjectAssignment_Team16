package com.example.atyourservice.mesaging.service;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;


import com.example.atyourservice.R;
import com.example.atyourservice.UserList;
import com.example.atyourservice.api.response.pojo.Messages;
import com.example.atyourservice.models.User;


public class MessagesRecycler extends AppCompatActivity {

        RecyclerView messagesRecycleView;
        private Messages msgs;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        String senderId = (String) getIntent().getSerializableExtra("Sender");
        String receiverId = (String) getIntent().getSerializableExtra("Receiver");

        int[] stickers = {R.drawable.thumbs_up, R.drawable.thumbs_down, R.drawable.love, R.drawable.celebrate, };
        //https://www.geeksforgeeks.org/gridview-using-baseadapter-in-android-with-example/
        GridAdapter gridAdapter = new GridAdapter(this, stickers, senderId, receiverId);
        GridView gridView = findViewById(R.id.GridView);
        gridView.setAdapter(gridAdapter);

        msgs = (Messages) getIntent().getSerializableExtra("Messages");

        messagesRecycleView = findViewById(R.id.messagesRecycler);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(MessagesRecycler.this);
        mLinearLayoutManager.setStackFromEnd(true);
        messagesRecycleView.setLayoutManager(mLinearLayoutManager);
        ChatMessageAdapter chatMessageAdapter = new ChatMessageAdapter(MessagesRecycler.this, msgs.getMessages());
        messagesRecycleView.setAdapter(chatMessageAdapter);
        messagesRecycleView.smoothScrollToPosition(chatMessageAdapter.getItemCount()-1);
    }
        public void onBackPressed(){
                String receiver = (String) getIntent().getSerializableExtra("Receiver");
                Intent chatList = new Intent(MessagesRecycler.this, UserList.class);
                User currentUser = new User(receiver);
                chatList.putExtra("userId", currentUser);
                startActivity(chatList);
        }
}



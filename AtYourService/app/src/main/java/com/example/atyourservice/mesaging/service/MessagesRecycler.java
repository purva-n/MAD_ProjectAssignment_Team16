package com.example.atyourservice.mesaging.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.GridView;


import com.example.atyourservice.R;
import com.example.atyourservice.UserList;
import com.example.atyourservice.api.response.pojo.Messages;
import com.example.atyourservice.models.Message;
import com.example.atyourservice.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class MessagesRecycler extends AppCompatActivity {

        RecyclerView messagesRecycleView;
        private String receiver_id;
        private String sender_id;

        private Messages messages;

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        String senderId = (String) getIntent().getSerializableExtra("Sender");
        String receiverId = (String) getIntent().getSerializableExtra("Receiver");

    }
        public void onBackPressed(){
                String receiver = (String) getIntent().getSerializableExtra("Receiver");
                Intent chatList = new Intent(MessagesRecycler.this, UserList.class);
                User currentUser = new User(receiver);
                chatList.putExtra("userId", currentUser);
                startActivity(chatList);
        }
}



package com.example.atyourservice.mesaging.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;

import android.os.Build;
import android.os.Bundle;

import com.example.atyourservice.R;
import com.example.atyourservice.UserList;
import com.example.atyourservice.models.User;


public class MessagesRecycler extends AppCompatActivity {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_messages);
        }

        @Override
        public void onBackPressed() {
                super.onBackPressed();

                Intent chatList = new Intent(MessagesRecycler.this, UserList.class);
                User sender = (User) getIntent().getSerializableExtra("Sender");
                chatList.putExtra("Sender", sender);
        }
}



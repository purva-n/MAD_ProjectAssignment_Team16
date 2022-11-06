package com.example.atyourservice.mesaging.service;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.example.atyourservice.UserList;
import com.example.atyourservice.api.response.pojo.Messages;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.example.atyourservice.R;
import com.example.atyourservice.models.Message;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private String receiver_id = "abc234";
    private String sender_id = "debra";
    private DatabaseReference dbfromSender;
    private DatabaseReference dbFromReceiver;
    private Messages messages;
    //retrieve current user id?

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        dbfromSender = FirebaseDatabase.getInstance().getReference().child("senders").child(sender_id).child("receivers").child(receiver_id).child("stickers");

        List<Message> messagesSent = new ArrayList<>();
        List<Message> messagesReceived = new ArrayList<>();
        messages = new Messages();

        dbfromSender.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    messagesSent.clear();

                    for (DataSnapshot stickerSnap : task.getResult().getChildren()) {
                        if(stickerSnap.exists()) {
                            System.out.println("stickerSnap " + stickerSnap.getValue());
                            Message m = stickerSnap.getValue(Message.class);
                            if(m != null) {
                                System.out.println("Sending!!!!!!!");
                                m.setFrom("sender");
                                messagesSent.add(m);
                            }

                        } else {
                            System.out.println("OOPS");
                        }
                    }
                }
            }
        });

        dbFromReceiver = FirebaseDatabase.getInstance().getReference().child("senders").child(receiver_id).child("receivers").child(sender_id).child("stickers");

        dbFromReceiver.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    messagesReceived.clear();

                    for (DataSnapshot stickerSnap : task.getResult().getChildren()) {
                        if(stickerSnap.exists()) {
                            System.out.println("stickerSnap " + stickerSnap.getValue());
                            Message m = stickerSnap.getValue(Message.class);
                            if(m != null) {
                                m.setFrom("receiver");
                                messagesReceived.add(m);
                            }
                        } else {
                            System.out.println("OOPS");
                        }
                    }

                    if(messagesSent.addAll(messagesReceived)) {
                        messagesSent.sort(Comparator.comparing(Message::getTimestamp));
                    }

                    for (Message m:
                            messagesSent) {
                        System.out.println(m.toString());
                    }
                    messages.setMessages(messagesSent);

                    Intent intent = new Intent(MessagesActivity.this, MessagesRecycler.class);
                    intent.putExtra("Messages", messages);
                    startActivity(intent);
                }
            }
        });
    }
}


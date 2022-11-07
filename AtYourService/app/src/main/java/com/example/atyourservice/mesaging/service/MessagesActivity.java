package com.example.atyourservice.mesaging.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.atyourservice.RegisterActivity;
import com.example.atyourservice.UserList;
import com.example.atyourservice.api.response.pojo.Messages;
import com.example.atyourservice.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.example.atyourservice.R;
import com.example.atyourservice.models.Message;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private String receiver_id;
    private String sender_id;
    private DatabaseReference dbfromSender;
    private DatabaseReference dbFromReceiver;
    private Messages messages;
    //retrieve current user id?

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        receiver_id = (String) getIntent().getSerializableExtra("receiverId");
        sender_id = (String) getIntent().getSerializableExtra("userId");

        System.out.println("**********SENDER " + sender_id + " RECEIVER "+ receiver_id);

        dbfromSender = FirebaseDatabase.getInstance().getReference().child("senders").child(sender_id).child("receivers");

        List<Message> messagesSent = new ArrayList<>();
        List<Message> messagesReceived = new ArrayList<>();
        messages = new Messages();

        dbfromSender.child(receiver_id).child("stickers").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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

        dbfromSender.child(receiver_id).child("stickers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()) {
                    System.out.println("stickerSnap " + snapshot.getValue());
                    Message m = snapshot.getValue(Message.class);
                    if(m != null) {
                        System.out.println("Sending!!!!!!!");
                        m.setFrom("sender");
                        messagesSent.add(m);
                    }
                    messages.setMessages(messagesSent);

                    Intent intent = new Intent(MessagesActivity.this, MessagesRecycler.class);
                    intent.putExtra("Messages", messages);
                    intent.putExtra("Sender", sender_id);
                    intent.putExtra("Receiver", receiver_id);
                    startActivity(intent);

                } else {
                    System.out.println("OOPS");
                }
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dbFromReceiver = FirebaseDatabase.getInstance().getReference().child("senders").child(receiver_id).child("receivers");

        dbFromReceiver.child(receiver_id).child("stickers").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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
                    intent.putExtra("Sender", sender_id);
                    intent.putExtra("Receiver", receiver_id);
                    startActivity(intent);
                }
            }
        });
    }


}


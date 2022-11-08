package com.example.atyourservice.mesaging.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.GridView;

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
    RecyclerView messagesRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        receiver_id = (String) getIntent().getSerializableExtra("Receiver");
        sender_id = (String) getIntent().getSerializableExtra("Sender");

        System.out.println("**********SENDER " + sender_id + " RECEIVER "+ receiver_id);

        int[] stickers = {R.drawable.thumbs_up, R.drawable.thumbs_down, R.drawable.love, R.drawable.celebrate, };
        //https://www.geeksforgeeks.org/gridview-using-baseadapter-in-android-with-example/
        GridAdapter gridAdapter = new GridAdapter(this, stickers, sender_id, receiver_id);
        GridView gridView = findViewById(R.id.GridView);
        gridView.setAdapter(gridAdapter);

        dbfromSender = FirebaseDatabase.getInstance().getReference().child("senders").child(sender_id).child("receivers");
        dbFromReceiver = FirebaseDatabase.getInstance().getReference().child("senders").child(receiver_id).child("receivers");
        messages = new Messages();

        List<Message> messagesSent = new ArrayList<>();
        List<Message> messagesReceived = new ArrayList<>();

        messagesRecycleView = findViewById(R.id.messagesRecycler);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(messagesRecycleView.getContext());
        mLinearLayoutManager.setStackFromEnd(true);
        messagesRecycleView.setLayoutManager(mLinearLayoutManager);

        dbfromSender.child(receiver_id).child("stickers").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    messagesSent.clear();

                    for (DataSnapshot stickerSnap : task.getResult().getChildren()) {
                        if(stickerSnap.exists()) {
                            System.out.println("stickerSnap " + stickerSnap.getValue());
                            Message m = stickerSnap.getValue(Message.class);
                            if(m != null && m.getTimestamp() != 0) {
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

        dbFromReceiver.child(sender_id).child("stickers").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    messagesReceived.clear();

                    for (DataSnapshot stickerSnap : task.getResult().getChildren()) {
                        if(stickerSnap.exists()) {
                            System.out.println("stickerSnap " + stickerSnap.getValue());
                            Message m = stickerSnap.getValue(Message.class);
                            if(m != null && m.getTimestamp() != 0) {
                                m.setFrom("receiver");
                                messagesReceived.add(m);
                            }
                        } else {
                            System.out.println("OOPS");
                        }
                    }

                    if(messages.getMessages() == null) {
                        System.out.println("HOLAAAA");
                        List<Message> ms;
                        ms = new ArrayList<Message>();
                        ms.addAll(messagesSent);
                        ms.addAll(messagesReceived);

                        ms.sort(Comparator.comparing(Message::getTimestamp));
                        messages.setMessages(ms);
                    } else {
                        if(messages.getMessages().addAll(messagesSent) && messages.getMessages().addAll(messagesReceived)) {
                            messages.getMessages().sort(Comparator.comparing(Message::getTimestamp));
                        }
                    }

                    //messages.setMessages(messagesSent);

                    if(messages.getMessages() != null && messages.getMessages().size() > 0) {
                        ChatMessageAdapter chatMessageAdapter = new ChatMessageAdapter(messagesRecycleView.getContext(), messages.getMessages());
                        messagesRecycleView.setAdapter(chatMessageAdapter);
                        messagesRecycleView.smoothScrollToPosition(chatMessageAdapter.getItemCount() - 1);
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
                    if(m != null && m.getTimestamp() != 0) {
                        System.out.println("Sending!!!!!!!");
                        m.setFrom("sender");
                        messagesSent.add(m);
                    }
                    messages.setMessages(messagesSent);
                } else {
                    System.out.println("OOPS");
                }

                if(messages.getMessages() != null && messages.getMessages().size() > 0) {
                    ChatMessageAdapter chatMessageAdapter = new ChatMessageAdapter(messagesRecycleView.getContext(), messages.getMessages());
                    messagesRecycleView.setAdapter(chatMessageAdapter);
                    messagesRecycleView.smoothScrollToPosition(chatMessageAdapter.getItemCount() - 1);
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

        dbFromReceiver.child(sender_id).child("stickers").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()) {
                    System.out.println("stickerSnap " + snapshot.getValue());
                    Message m = snapshot.getValue(Message.class);
                    if(m != null && m.getTimestamp() != 0) {
                        System.out.println("Sending!!!!!!!");
                        m.setFrom("receiver");
                        messagesReceived.add(m);
                    }
                    messages.setMessages(messagesReceived);
                } else {
                    System.out.println("OOPS");
                }

                if(messages.getMessages() != null && messages.getMessages().size() > 0) {
                    ChatMessageAdapter chatMessageAdapter = new ChatMessageAdapter(messagesRecycleView.getContext(), messages.getMessages());
                    messagesRecycleView.setAdapter(chatMessageAdapter);
                    messagesRecycleView.smoothScrollToPosition(chatMessageAdapter.getItemCount() - 1);
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


        /*ChatMessageAdapter chatMessageAdapter = new ChatMessageAdapter(MessagesActivity.this, msgs.getMessages());
        messagesRecycleView.setAdapter(chatMessageAdapter);
        messagesRecycleView.smoothScrollToPosition(chatMessageAdapter.getItemCount()-1);
*/

    }


}


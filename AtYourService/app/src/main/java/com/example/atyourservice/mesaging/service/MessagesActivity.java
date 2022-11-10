package com.example.atyourservice.mesaging.service;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;

import com.example.atyourservice.api.response.pojo.Messages;
import com.example.atyourservice.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.atyourservice.R;
import com.example.atyourservice.models.Message;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private User receiver;
    private User sender;
    private DatabaseReference dbfromSender;
    private DatabaseReference dbFromReceiver;
    private Messages messages;
    RecyclerView messagesRecycleView;
    private List<Message> messagesSent;
    private List<Message> messagesReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        receiver = (User) getIntent().getSerializableExtra("Receiver");
        sender = (User) getIntent().getSerializableExtra("Sender");

        String receiverId = receiver.getUserId();
        String senderId = sender.getUserId();

        int[] stickers = {R.drawable.thumbs_up, R.drawable.thumbs_down, R.drawable.love, R.drawable.celebrate, };
        //https://www.geeksforgeeks.org/gridview-using-baseadapter-in-android-with-example/
        GridAdapter gridAdapter = new GridAdapter(this, stickers, sender, receiver
        );
        GridView gridView = findViewById(R.id.GridView);
        gridView.setAdapter(gridAdapter);

        dbfromSender = FirebaseDatabase.getInstance().getReference().child("senders").child(senderId).child("receivers");
        dbFromReceiver = FirebaseDatabase.getInstance().getReference().child("senders").child(receiverId).child("receivers");
        messages = new Messages();

        messagesSent = new ArrayList<>();
        messagesReceived = new ArrayList<>();

        messagesRecycleView = findViewById(R.id.messagesRecycler);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(messagesRecycleView.getContext());
        mLinearLayoutManager.setStackFromEnd(true);
        messagesRecycleView.setLayoutManager(mLinearLayoutManager);

        //Important
        getChatHistory(senderId, receiverId);

        dbfromSender.child(receiverId).child("stickers").addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()) {
                    messages.getMessages().clear();
                    messagesSent.clear();

                    getChatHistory(senderId, receiverId);
                    Message m = snapshot.getValue(Message.class);
                    if(m != null && m.getTimestamp() != 0) {
                        m.setFrom("sender");
                        messagesSent.add(m);
                    }
                    messages.getMessages().addAll(messagesSent);
                    messages.getMessages().sort(Comparator.comparing(Message::getTimestamp));
                } else {
                    Toast.makeText(MessagesActivity.this, "There was a glitch", Toast.LENGTH_SHORT).show();
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

        dbFromReceiver.child(senderId).child("stickers").addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()) {
                    messagesReceived.clear();
                    Message m = snapshot.getValue(Message.class);
                    if(m != null && m.getTimestamp() != 0) {
                        m.setFrom("receiver");
                        messagesReceived.add(m);
                    }

                    messages.getMessages().addAll(messagesReceived);
                    messages.getMessages().sort(Comparator.comparing(Message::getTimestamp));

                } else {
                    Toast.makeText(MessagesActivity.this, "There was a glitch", Toast.LENGTH_SHORT).show();
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
    }

    private void getChatHistory(String senderId, String receiverId) {
        dbfromSender.child(receiverId).child("stickers").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    messagesSent.clear();

                    for (DataSnapshot stickerSnap : task.getResult().getChildren()) {
                        if(stickerSnap.exists()) {
                            Message m = stickerSnap.getValue(Message.class);
                            if(m != null && m.getTimestamp() != 0) {
                                m.setFrom("sender");
                                messagesSent.add(m);
                            }

                        } else {
                            Toast.makeText(MessagesActivity.this, "There was a glitch", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        dbFromReceiver.child(senderId).child("stickers").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    messagesReceived.clear();

                    for (DataSnapshot stickerSnap : task.getResult().getChildren()) {
                        if(stickerSnap.exists()) {
                            Message m = stickerSnap.getValue(Message.class);
                            if(m != null && m.getTimestamp() != 0) {
                                m.setFrom("receiver");
                                messagesReceived.add(m);
                            }
                        } else {
                            Toast.makeText(MessagesActivity.this, "There was a glitch", Toast.LENGTH_SHORT).show();
                        }
                    }

                    messages.getMessages().clear();

                    if(messages.getMessages().addAll(messagesSent) && messages.getMessages().addAll(messagesReceived)) {
                        messages.getMessages().sort(Comparator.comparing(Message::getTimestamp));
                    }

                    if(messages.getMessages() != null && messages.getMessages().size() > 0) {
                        ChatMessageAdapter chatMessageAdapter = new ChatMessageAdapter(messagesRecycleView.getContext(), messages.getMessages());
                        messagesRecycleView.setAdapter(chatMessageAdapter);
                        messagesRecycleView.smoothScrollToPosition(chatMessageAdapter.getItemCount() - 1);
                    }
                }
            }
        });
    }


}


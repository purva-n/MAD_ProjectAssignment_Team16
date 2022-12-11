package com.example.atyourservice.togather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.atyourservice.R;
import com.example.atyourservice.api.response.pojo.Chats;
import com.example.atyourservice.api.response.pojo.Groups;
import com.example.atyourservice.api.response.pojo.Messages;
import com.example.atyourservice.models.Chat;
import com.example.atyourservice.models.Group;
import com.example.atyourservice.models.Message;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class GroupChatActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private String groupId, messageId;
    private Toolbar toolbar;
    private ImageView groupIconIv;
    private TextView groupNameTv;
    private ImageButton attachBtn,sendBtn;
    private EditText messageEt;
    private RecyclerView chatRv;
    private ArrayList<Group> groupChatList;
    private AdapterGroupChat adapterGroupChat;
    private Chats messageList;
    private String currentuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        //toolbar = findViewById(R.id.toolbarofspecificchat);
        groupIconIv = findViewById(R.id.groupIconIv);
        groupNameTv = findViewById(R.id.groupnametv);
        attachBtn = findViewById(R.id.attachBtn);
        sendBtn = findViewById(R.id.sendBtn);
        messageEt = findViewById(R.id.getmessage);
        chatRv = findViewById(R.id.chatRv);

        Intent intent = getIntent();
        Group group = ((Group) intent.getSerializableExtra("group"));
        groupId = group.getId();
        groupNameTv.setText(group.getName());
        messageList = new Chats();

        currentuser = "uuid2";

        loadGroupMessage();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEt.getText().toString().trim();
                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(GroupChatActivity.this, "Can't send empty message...", Toast.LENGTH_SHORT).show();
                } else {
                    sendMessage(message);
                }
            }
        });

    }

    private void loadGroupMessage() {
        List<String> messageKeys = new ArrayList<>();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("groups");
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference messageDb = dbRef.child("messages").child(groupId);

        adapterGroupChat = new AdapterGroupChat(GroupChatActivity.this, messageList.getChats());
        messageList.getChats().clear();
        messageDb.addChildEventListener(new ChildEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    Chat message = snapshot.getValue(Chat.class);
                    if (message != null) {
                        if(message.getSenderid().equalsIgnoreCase(currentuser)) {
                            message.setFrom("sender");
                        }
                        messageList.getChats().add(message);
                        messageList.getChats().sort(Comparator.comparing(Chat::getTimestamp));
                        adapterGroupChat.updateGroupChat(messageList.getChats());
                    }
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

        chatRv.setAdapter(adapterGroupChat);

    }

    private void sendMessage(String message) {
        long timestamp = System.currentTimeMillis();
        String uid = "uuid2";

        Chat chat = new Chat();
        chat.setChat(message);
        chat.setSenderid(uid);
        chat.setTimestamp(timestamp);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("messages");
        reference.child(groupId).push().setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                messageEt.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GroupChatActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
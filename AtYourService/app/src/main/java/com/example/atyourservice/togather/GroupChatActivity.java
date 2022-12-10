package com.example.atyourservice.togather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

//import org.checkerframework.checker.units.qual.A;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class GroupChatActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private String groupId, messageId;
    private Toolbar toolbar;
    private ImageView groupIconIv;
    private TextView groupNameTv;
    private ImageButton attachBtn,sendBtn;
    private EditText messageEt;
    private RecyclerView chatRv;
    private ArrayList<ModelGroupChat> groupChatList;
    private AdapterGroupChat adapterGroupChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        toolbar = findViewById(R.id.toolbarofspecificchat);
        groupIconIv = findViewById(R.id.groupIconIv);
        groupNameTv = findViewById(R.id.groupnametv);
        attachBtn = findViewById(R.id.attachBtn);
        sendBtn = findViewById(R.id.sendBtn);
        messageEt = findViewById(R.id.getmessage);
        chatRv = findViewById(R.id.chatRv);

        Intent intent = getIntent();
        messageId = intent.getStringExtra("groupid");

        firebaseAuth = FirebaseAuth.getInstance();
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
        groupChatList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("messages");
        reference.child("groupid").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupChatList.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ModelGroupChat model = ds.getValue(ModelGroupChat.class);
                    groupChatList.add(model);
                }
                adapterGroupChat = new AdapterGroupChat(GroupChatActivity.this, groupChatList);
                chatRv.setAdapter(adapterGroupChat);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError){

            }
        });
    }

    private void sendMessage(String message) {
        String timestamp = ""+System.currentTimeMillis();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("senderid",""+firebaseAuth.getUid());
        hashMap.put("content",""+message);
        hashMap.put("send_time",""+timestamp);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("messages");
        reference.child("groupid").child("messageid").child("send_time").setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
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
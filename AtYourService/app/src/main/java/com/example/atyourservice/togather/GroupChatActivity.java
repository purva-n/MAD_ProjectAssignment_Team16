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
import com.example.atyourservice.api.response.pojo.Notifications;
import com.example.atyourservice.models.Chat;
import com.example.atyourservice.models.Group;
import com.example.atyourservice.models.Message;
import com.example.atyourservice.models.Notification;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    private String groupId, messageId;
    private Toolbar toolbar;
    private ImageView groupIconIv;
    private TextView groupNameTv;
    private ImageButton attachBtn,sendBtn;
    private EditText messageEt;
    private RecyclerView chatRv;
    private AdapterGroupChat adapterGroupChat;
    private Chats messageList;
    private GoogleSignInClient mGoogleSignInClient;
    private String currentuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(GroupChatActivity.this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(GroupChatActivity.this);
        if (acct != null) {
            currentuser = acct.getEmail();
            currentuser = currentuser.substring(0, currentuser.length() - 10).replace(".", "_");
        }

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

        loadGroupMessage();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEt.getText().toString().trim();
                if (TextUtils.isEmpty(message)) {
                    Toast.makeText(GroupChatActivity.this, "Can't send empty message...", Toast.LENGTH_SHORT).show();
                } else {
                    sendMessage(message);
                    //below is for
                    DatabaseReference firebaseRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference users =firebaseRef.child("groups").child(groupId).child("users");
                    ArrayList<String> notiReceivers= new ArrayList<String>();
                    users.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for( DataSnapshot snapshot1: snapshot.getChildren()){
                                String receivers = snapshot1.getValue(String.class);
                                if(!receivers.equalsIgnoreCase(currentuser)){
                                    notiReceivers.add(receivers);
                                }}
                            for(String receiver: notiReceivers){
                                firebaseRef.child("users").child(receiver).child("notification").push().setValue(new Notification(groupId,message));
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
            }
        });

        setListeners();
    }

    private void setListeners() {
        chatRv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom > oldBottom)
                    if (messageList.getChats().size()==0){
                        chatRv.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                chatRv.smoothScrollToPosition(messageList.getChats().size());
                            }
                        }, 100);
                    }else{
                        chatRv.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                chatRv.smoothScrollToPosition(messageList.getChats().size()-1);
                            }
                        }, 100);
                    }
            }
        });

    }

    private void loadGroupMessage() {
        List<String> messageKeys = new ArrayList<>();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("groups");
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        adapterGroupChat = new AdapterGroupChat(GroupChatActivity.this, messageList.getChats());
        messageList.getChats().clear();

        DatabaseReference messageDb = dbRef.child("messages").child(groupId);
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

        Chat chat = new Chat();
        chat.setChat(message);
        chat.setSenderid(currentuser);
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
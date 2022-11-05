package com.example.atyourservice.ChatPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import com.example.atyourservice.GridAdapter;
import com.example.atyourservice.R;
import com.example.atyourservice.models.Message;

import java.util.ArrayList;

public class MessagesActivity extends AppCompatActivity {
    private ArrayList<Message> messagesReceived;
    public String receiver_id;
    public String sender_id;
    //retrieve current user id?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        int[] stickers = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground, R.drawable.image1, R.drawable.image2,
                R.drawable.image3};
        //https://www.geeksforgeeks.org/gridview-using-baseadapter-in-android-with-example/
        GridAdapter gridAdapter = new GridAdapter(MessagesActivity.this, stickers);
        GridView gridView = findViewById(R.id.GridView);
        gridView.setAdapter(gridAdapter);
        messagesReceived = new ArrayList<Message>();
       DatabaseReference db = FirebaseDatabase.getInstance().getReference().child(sender_id).child(receiver);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                messagesReceived.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    messagesReceived.add( new Message(snapshot1.)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/
    }
}


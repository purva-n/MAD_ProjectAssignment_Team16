package com.example.atyourservice.ChatPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.GridView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.example.atyourservice.GridAdapter;
import com.example.atyourservice.R;
import com.example.atyourservice.models.Message;

import java.util.ArrayList;

public class MessagesActivity extends AppCompatActivity {
    private ArrayList<Message> messagesReceived;
    public String receiver_id= "abc234";
    public String sender_id="abc123";
    //retrieve current user id?

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        int[] stickers = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground, R.drawable.image1, R.drawable.image2,
                R.drawable.image3};
        //https://www.geeksforgeeks.org/gridview-using-baseadapter-in-android-with-example/
      /*  GridAdapter gridAdapter = new GridAdapter(MessagesActivity.this, stickers);
        GridView gridView = findViewById(R.id.GridView);
        gridView.setAdapter(gridAdapter);

       */
        messagesReceived = new ArrayList<Message>();
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("senders").child(sender_id).child("receivers").child(receiver_id).child("stickers");
        messagesReceived.clear();
        db.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });

        /*for (DataSnapshot snapshot3 : snapshot.getChildren()) {
            messagesReceived.add(snapshot1.getValue(Message.class));
        }
        System.out.println(messagesReceived);
    }*/
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                messagesReceived.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    messagesReceived.add(snapshot1.getValue(Message.class));
                }
                System.out.println("Message received " + messagesReceived);
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }
}


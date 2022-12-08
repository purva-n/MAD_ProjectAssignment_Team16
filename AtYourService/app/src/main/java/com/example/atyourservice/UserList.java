package com.example.atyourservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.atyourservice.models.User;
import com.example.atyourservice.users.pojo.Stickers;
import com.example.atyourservice.users.pojo.UserIds;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    UserAdapter userAdapter;
    ArrayList<User> list;
    String currUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        FirebaseApp.initializeApp(this);

        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        database.child("senders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    list.clear();
                    int i =0;

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        System.out.println("COUNTER::: " + i++);
                        String receiver = dataSnapshot.getKey();
                        User currentUser = (User) getIntent().getSerializableExtra("Sender");
                        currUser = currentUser.getUserId();
                        System.out.println("SETTING USER ID :::: " + receiver);
                        if(!receiver.equalsIgnoreCase(currentUser.getUserId())){
                            String receiverToken = snapshot.child(receiver).child("Token").getValue().toString();
                            User u = new User(receiver, receiverToken);
                            list.add(u);

//                            List<Stickers> stickers = new ArrayList<>();
//                            Stickers sticker = new Stickers();
//                            stickers.add(sticker);
//                            UserIds user = new UserIds(stickers);
//
//                            database.child("senders").child(currUser).child("receivers").child(receiver).setValue(user);
                        }
                    }
                }

                userAdapter.notifyDataSetChanged();
                //list.clear();
                User currentUser = (User) getIntent().getSerializableExtra("Sender");
                currUser = currentUser.getUserId();
                userAdapter = new UserAdapter(UserList.this, list, currentUser);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        User currentUser = (User) getIntent().getSerializableExtra("Sender");
        currUser = currentUser.getUserId();

        userAdapter = new UserAdapter(UserList.this, list, currentUser);
        recyclerView.setAdapter(userAdapter);
    }

    public void onBackPressed(){
        //https://stackoverflow.com/questions/10905945/android-prompting-an-alertdialog-onbackpressed
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to return to Login Page?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent2 = new Intent(UserList.this,RegisterActivity.class);
                        startActivity(intent2);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}


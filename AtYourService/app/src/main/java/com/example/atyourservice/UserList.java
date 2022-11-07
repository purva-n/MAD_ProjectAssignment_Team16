package com.example.atyourservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.atyourservice.models.User;
import com.example.atyourservice.users.pojo.Stickers;
import com.example.atyourservice.users.pojo.UserIds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        recyclerView = findViewById(R.id.userList);
        database = FirebaseDatabase.getInstance().getReference();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        database.child("senders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int i =0;

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        System.out.println("COUNTER::: " + i++);
                        String receiver = dataSnapshot.getKey();
                        User currentUser = (User) getIntent().getSerializableExtra("userId");
                        currUser = currentUser.getUserId();
                        System.out.println("SETTING USER ID :::: " + receiver);
                        if(!receiver.equalsIgnoreCase(currentUser.getUserId())){
                            User u = new User(receiver);
                            list.add(u);

                            List<Stickers> stickers = new ArrayList<>();
                            Stickers sticker = new Stickers();
                            stickers.add(sticker);
                            UserIds user = new UserIds(stickers);

                            database.child("senders").child(currUser).child("receivers").child(receiver).setValue(user);
                        }
                    }
                }
                userAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        User currentUser = (User) getIntent().getSerializableExtra("userId");
        currUser = currentUser.getUserId();

        userAdapter = new UserAdapter(UserList.this, list, currUser);
        recyclerView.setAdapter(userAdapter);
    }
}

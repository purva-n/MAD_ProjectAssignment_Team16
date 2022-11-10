package com.example.atyourservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.atyourservice.mesaging.service.MessagesRecycler;
import com.example.atyourservice.models.User;
import com.example.atyourservice.users.pojo.Stickers;
import com.example.atyourservice.users.pojo.UserIds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private EditText userNameInput;
    private Button register,about,login;
    private String DEVICE_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("senders");

        userNameInput = (EditText) findViewById(R.id.userName);
        register = findViewById(R.id.btnReg);
        login = findViewById(R.id.btnLogin);
        about = findViewById(R.id.btnAbout);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               registerUser();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,AboutActivity.class));
            }
        });

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token failed");
                            return;
                        }

                        // Get new FCM registration token
                        DEVICE_TOKEN = task.getResult();
                        System.out.println(" TOKEN IS :::: " + DEVICE_TOKEN);
                    }
                });
    }

    private void registerUser() {
        String regUser = userNameInput.getText().toString();
        System.out.println("REG USERRRRR:: " + regUser);
        if (regUser == null || regUser.equals("")) {
            userNameInput.setError("Please Enter Username!");
        } else {
            System.out.println("I AM HERE !");
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    System.out.println("SNAPSHOT KEY : " + snapshot.getKey());
                    if (snapshot.getKey().equalsIgnoreCase(regUser)) {
                        Toast.makeText(RegisterActivity.this, "The username is already registered", Toast.LENGTH_SHORT).show();
                    } else {
                        List<Stickers> stickers = new ArrayList<>();
                        Stickers sticker = new Stickers();
                        stickers.add(sticker);
                        UserIds user = new UserIds(stickers);
                        List<UserIds> users = new ArrayList<>();
                        users.add(user);
                        databaseReference.child(regUser).child("receivers").setValue(users);
                        databaseReference.child(regUser).child("Token").setValue(DEVICE_TOKEN);

                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
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
    }

    private void loginUser() {
        final String logUser = userNameInput.getText().toString();
        if (logUser == null || logUser.equalsIgnoreCase("")) {
            userNameInput.setError("Please enter username!");
        } else{
            databaseReference.child(logUser).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if (snapshot.exists()){
                        Toast.makeText(RegisterActivity.this,"Login Successful.!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this,UserList.class);
                        User currentUser = new User(logUser, DEVICE_TOKEN);
                        intent.putExtra("Sender", currentUser);
                        startActivity(intent);
                    } else {
                        Toast.makeText(RegisterActivity.this,"User not registered. Please register first",Toast.LENGTH_SHORT).show();
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
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mainPage = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainPage);
    }
}

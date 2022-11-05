package com.example.atyourservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.RenderProcessGoneDetail;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.atyourservice.RegisterActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RegisterActivity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mad-assignment-8-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText userNameInput = (EditText) findViewById(R.id.userName);
        final Button register = findViewById(R.id.btnReg);
        final Button login = findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String logUser = userNameInput.getText().toString();
//                final String dummy = logUser+"123";
                final String password = "123456";
                startActivity(new Intent(RegisterActivity.this,UserList.class));
//                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                if (logUser == null) {
                    userNameInput.setError("Please enter username!");
                }else{
                    databaseReference.child("senders").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(logUser)){
                                final String getPassword = snapshot.child(logUser).child("Password").getValue(String.class);
                                if (getPassword.equals(password)){
                                    Toast.makeText(RegisterActivity.this,"Logged in successfully!",Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(RegisterActivity.this,"Logged in unsuccessfully!",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(RegisterActivity.this,"User registered successfully",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String regUser = userNameInput.getText().toString();
//                final String dummy = regUser+"123";
                final String password = "123456";
                final String uriImage = "android.resource://com.example.atyourservice/drawable/image1";
                if (regUser == null) {
                    userNameInput.setError("Please Enter Username!");
                }else{
                    databaseReference.child("senders").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(regUser)){
                                Toast.makeText(RegisterActivity.this,"The username is already registered",Toast.LENGTH_SHORT).show();
                            }else{
                                databaseReference.child("senders").child(regUser).child("Username").setValue(regUser);
                                databaseReference.child("senders").child(regUser).child("Password").setValue(password);
                                databaseReference.child("senders").child(regUser).child("UriImage").setValue(uriImage);
                                Toast.makeText(RegisterActivity.this,"User registered successfully",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
    }
}

package com.example.atyourservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.atyourservice.togather.HomePageActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button firebaseButton = findViewById(R.id.fireBaseAssignment);
        firebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, RegisterActivity.class);
                //Intent intent1 = new Intent(MainActivity.this, MessagesActivity.class);
                startActivity(intent1);
            }
        });

        Button toGatherButton = findViewById(R.id.toGatherButton);
        toGatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, HomePageActivity.class);
                startActivity(intent2);
            }
        });

    }

    public void onClick(View view) {
        Intent intent = new Intent(this, EventSearchActivity.class);
        startActivity(intent);
    }

}
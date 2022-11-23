package com.example.atyourservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        Button toGather = findViewById(R.id.toGather);

        toGather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchToGather();
            }
        });
    }

    private void launchToGather() {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, EventSearchActivity.class);
        startActivity(intent);
    }

}
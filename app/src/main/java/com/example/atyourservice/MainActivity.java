package com.example.atyourservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button toGatherButton = findViewById(R.id.toGather);
        toGatherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // need to add chat lists activity below
                // Intent intent1 = new Intent(this,{Insert Chat List activity});
                //startActivity(intent1);
            }
        });
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, EventSearchActivity.class);
        startActivity(intent);
    }

}
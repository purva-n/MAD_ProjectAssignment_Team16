//package com.example.atyourservice;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//public class MainActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        Button toGatherButton = findViewById(R.id.toGather);
//        toGatherButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // need to add chat lists activity below
//                // Intent intent1 = new Intent(this,{Insert Chat List activity});
//                //startActivity(intent1);
//            }
//        });
//    }
//
//    public void onClick(View view) {
//        Intent intent = new Intent(this, EventSearchActivity.class);
//        startActivity(intent);
//    }
//
//}

package com.example.atyourservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAYService,btnFirebase,btnToGather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAYService = (Button) findViewById(R.id.Week7Assignment);
        btnAYService.setOnClickListener(this);
        btnFirebase = (Button) findViewById(R.id.fireBaseAssignment);
        btnFirebase.setOnClickListener(this);
        btnToGather = (Button) findViewById(R.id.toGather);
        btnToGather.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.Week7Assignment:
                openAtYourServiceActivity();
                break;
            case R.id.fireBaseAssignment:
                openFirebaseActivity();
                break;
            case R.id.toGather:
                openToGatherActivity();
                break;
        }
    }

    private void openToGatherActivity() {

    }

    public void openAtYourServiceActivity(){
        Intent intent = new Intent(this,EventSearchActivity.class);
        startActivity(intent);
    }
    public void openFirebaseActivity(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

}
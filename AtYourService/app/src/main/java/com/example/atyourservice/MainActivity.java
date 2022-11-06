<<<<<<< HEAD
=======
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

>>>>>>> 5df33ab (add about me and user profile image)
package com.example.atyourservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

<<<<<<< HEAD
import com.example.atyourservice.ChatPackage.MessagesActivity;

public class MainActivity extends AppCompatActivity {
=======
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnAYService,btnFirebase,btnToGather;
>>>>>>> 5df33ab (add about me and user profile image)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
        Button firebaseButton = findViewById(R.id.fireBaseAssignment);
        firebaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, MessagesActivity.class);
                startActivity(intent1);
            }
        });
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, EventSearchActivity.class);
        startActivity(intent);
    }

}
=======

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

>>>>>>> 5df33ab (add about me and user profile image)

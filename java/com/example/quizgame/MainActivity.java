package com.example.quizgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    TextView signOut;
    Button start;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signOut = findViewById(R.id.textViewSignOut);
        start = findViewById(R.id.buttonStart);
        signOut.setOnClickListener(v -> {
           auth.signOut();
           Intent i = new Intent(MainActivity.this,Login_page.class);
           startActivity(i);
           finish();
        });
        start.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this, Quiz_Page.class);
                startActivity(i);
            }
        });
    }
}
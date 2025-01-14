package com.example.quizgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash_Screen extends AppCompatActivity {
    ImageView image;
    TextView title;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        image = findViewById(R.id.imageViewSplash);
        title = findViewById(R.id.textViewSplash);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.splash_anim);
        title.startAnimation(animation);
        new Handler().postDelayed(() -> {
          Intent i = new Intent(Splash_Screen.this,Login_page.class);
          startActivity(i);


        },5000);
    }
}
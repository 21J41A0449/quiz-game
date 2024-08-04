package com.example.quizgame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Forgot_Password extends AppCompatActivity {
    EditText mail;
    Button button;
    ProgressBar progressBar;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mail = findViewById(R.id.editTextpasswordEmail);
        button = findViewById(R.id.buttonpassword);
        progressBar = findViewById(R.id.progressBarForgotPassword);
        button.setOnClickListener(v -> {
           String userEmail = mail.getText().toString();
           resetPassword(userEmail);
       });
    }
    public void resetPassword(String userEmail)
    {
        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()
            ) {
                Toast.makeText(Forgot_Password.this, "we sent an email to reset your password",
                        Toast.LENGTH_LONG).show();
                button.setClickable(false);
                progressBar.setVisibility(View.INVISIBLE);
                finish();

            }
            else {
                Toast.makeText(Forgot_Password.this, "Sorry, there is a problem, please try again later...",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
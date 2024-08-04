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

public class Sign_Up_Page extends AppCompatActivity {
    EditText mail;
    EditText password;
    Button signUp;
    ProgressBar progressBar;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
        mail = findViewById(R.id.editTextSignupmail);
        password = findViewById(R.id.editTextSignupPassword);
        signUp = findViewById(R.id.buttonSignupsign);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        signUp.setOnClickListener(v -> {
            signUp.setClickable(false);
           String userEmail = mail.getText().toString();
           String userPassword = password.getText().toString();
           signUpFirebase(userEmail, userPassword);

        });
    }
    public void signUpFirebase(String userEmail, String userpassword){
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(userEmail,userpassword)
                .addOnCompleteListener(this, task -> {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(Sign_Up_Page.this, "Your account is created"
                                ,Toast.LENGTH_LONG).show();
                        finish();
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                    else {
                        Toast.makeText(Sign_Up_Page.this, "There is a problem, please try again later"
                                ,Toast.LENGTH_LONG).show();
                    }
                });

    }
}
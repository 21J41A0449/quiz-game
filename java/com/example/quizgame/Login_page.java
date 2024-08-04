package com.example.quizgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login_page extends AppCompatActivity {
    EditText password;
    Button signIn;
    SignInButton signInGoogle;
    TextView signUp;
    TextView forgotPassword;
    EditText mail;
    ProgressBar ProgressBarSignin;
    GoogleSignInClient googleSignInClient;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ActivityResultLauncher<Intent> activityResultLauncher;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        registerActivityForGoogleSignIn();
         password = findViewById(R.id.editTextLoginPassword);
         signIn = findViewById(R.id.LoginSignin);
         signInGoogle = findViewById(R.id.LoginGoogleSignin);
         mail = findViewById(R.id.editTextLoginEmail);
         signUp = findViewById(R.id.textViewLoginSignup);
         forgotPassword = findViewById(R.id.textViewLoginForgotpassword);
         ProgressBarSignin = findViewById(R.id.progressBar4);
         signIn.setOnClickListener(v -> {
            String userEmail = mail.getText().toString();
            String userPassword = password.getText().toString();

            signInWithFirebase(userEmail, userPassword);
        });
        signInGoogle.setOnClickListener(v -> signInGoogle());
        signUp.setOnClickListener(v -> {
            Intent i = new Intent(Login_page.this,Sign_Up_Page.class);
            startActivity(i);
            finish();
        });
        forgotPassword.setOnClickListener(v -> {
            Intent i = new Intent(Login_page.this,Forgot_Password.class);
            startActivity(i);

        });
    }

    public void signInGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1032535255962")
                .requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this,gso);
        signIn();
    }
    public void signIn(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        activityResultLauncher.launch(signInIntent);
    }
    public void registerActivityForGoogleSignIn(){
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        int resultCode = result.getResultCode();
                        Intent data = result.getData();
                        if (resultCode == RESULT_OK && data != null) {
                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            firebaseSignInWithGoogle(task);
                        }
                    }
                });

    }
    private void firebaseSignInWithGoogle(Task<GoogleSignInAccount>  task)  {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Toast.makeText(this, "Successfully", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Login_page.this, MainActivity.class);
            startActivity(i);
            finish();
            firebaseGoogleAccount(account);
        }catch (ApiException e){
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
       }
    }
    private void firebaseGoogleAccount(GoogleSignInAccount account){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        auth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, task -> {

                        // FirebaseUser user =

                });
    }
    public void signInWithFirebase(String userEmail, String userPassword)
    {
        ProgressBarSignin.setVisibility(View.VISIBLE);

    signIn.setClickable(false);
        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Intent i = new Intent(Login_page.this, MainActivity.class);
                        startActivity(i);
                        finish();
                        ProgressBarSignin.setVisibility(View.INVISIBLE);
                        Toast.makeText(Login_page.this, "Sign In is successful"
                                , Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login_page.this, "Sign In is not successful"
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = auth.getCurrentUser();
        if(user!= null)
        {
            Intent i = new Intent(Login_page.this, MainActivity.class);
            startActivity(i);
            finish();
        }
    }
}
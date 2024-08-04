package com.example.quizgame;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Quiz_Page extends AppCompatActivity {
    TextView Time, Correct, Wrong;
    TextView Question, A,B,C,D;
    Button Next, Finish;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference().child("Questions");
    String quizQuestion;
    String quizAnswerA;
    String quizAnswerB;
    String quizAnswerC;
    String quizAnswerD;
    String quizCorrectAnswer;
    int questionCount;
    String userAnswer;
    int userCorrect =0;
    int userWrong = 0;
    int questionNumber =1;
    CountDownTimer countDownTimer;
    private static final long TOTAL_TIME =25000;
    Boolean timerContinue;
    long leftTime = TOTAL_TIME;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_page);
        Time = findViewById(R.id.textViewTime);
        Correct = findViewById(R.id.textViewCorrect);
        Wrong = findViewById(R.id.textViewWrong);
        Question = findViewById(R.id.textViewQuestion);
        A = findViewById(R.id.textViewA);
        B = findViewById(R.id.textViewB);
        C= findViewById(R.id.textViewC);
        D = findViewById(R.id.textViewD);
        Next = findViewById(R.id.buttonNext);
        Finish = findViewById(R.id.buttonFinish);
        game();
        Next.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        resetTimer();
                                        game();
                                    }
                                });

                Finish.setOnClickListener(v -> {

                });
        A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer ="A";
                if(quizCorrectAnswer.equals(userAnswer))
                {
                    A.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                }
                else {
                    A.setBackgroundColor(Color.RED);
                    userWrong++;
                    findAnswer();
                }
            }
        });
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer ="B";
                if(quizCorrectAnswer.equals(userAnswer))
                {
                    B.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                }
                else {
                    B.setBackgroundColor(Color.RED);
                    userWrong++;
                    findAnswer();
                }
            }
        });
        C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer ="C";
                if(quizCorrectAnswer.equals(userAnswer))
                {
                    C.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                }
                else {
                    C.setBackgroundColor(Color.RED);
                    userWrong++;
                    findAnswer();
                }
            }
        });
        D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                userAnswer ="D";
                if(quizCorrectAnswer.equals(userAnswer))
                {
                    D.setBackgroundColor(Color.GREEN);
                    userCorrect++;
                }
                else {
                    D.setBackgroundColor(Color.RED);
                    userWrong++;
                    findAnswer();
                }
            }
        });
    }
    public void game()
    {
        startTimer();
        A.setBackgroundColor(Color.WHITE);
        B.setBackgroundColor(Color.WHITE);
        C.setBackgroundColor(Color.WHITE);
        D.setBackgroundColor(Color.WHITE);

        // Read from the database
        databaseReference .addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange( DataSnapshot  dataSnapshot) {
                questionCount = (int) dataSnapshot.getChildrenCount();
                quizQuestion = dataSnapshot.child(String.valueOf(questionNumber)).child("q").getValue().toString();
                quizAnswerA = dataSnapshot.child(String.valueOf(questionNumber)).child("A").getValue().toString();
                quizAnswerB= dataSnapshot.child(String.valueOf(questionNumber)).child("B").getValue().toString();
                quizAnswerC = dataSnapshot.child(String.valueOf(questionNumber)).child("C").getValue().toString();
                quizAnswerD = dataSnapshot.child(String.valueOf(questionNumber)).child("D").getValue().toString();
                quizCorrectAnswer = dataSnapshot.child(String.valueOf(questionNumber)).child("answer").getValue().toString();
                Question.setText(quizQuestion);
                A.setText(quizAnswerA);
                B.setText(quizAnswerB);
                C.setText(quizAnswerC);
                D.setText(quizAnswerD);
                if(questionNumber< questionCount)
                {
                    questionNumber++;
                }
                else {
                    Toast.makeText(Quiz_Page.this,"You answered all questions.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {
                Toast.makeText(Quiz_Page.this,"There is an error.",
                        Toast.LENGTH_SHORT).show();
            }




    }) ;
    }
    public void findAnswer()
    {
        if(quizCorrectAnswer.equals(userAnswer)) {
            A.setBackgroundColor(Color.GREEN);
        }
        else if(quizCorrectAnswer.equals("B"))
        {
            B.setBackgroundColor(Color.GREEN);
        }else if(quizCorrectAnswer.equals("C"))
        {
            C.setBackgroundColor(Color.GREEN);
        }else if(quizCorrectAnswer.equals("D"))
        {
            D.setBackgroundColor(Color.GREEN);
        }
    }

    public void startTimer()
    {
        countDownTimer = new CountDownTimer(leftTime,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                leftTime = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerContinue = false;
                pauseTimer();
                Question.setText("Sorry, time is up!");
            }
        }.start();
        timerContinue = true;
    }
    public void resetTimer()
    {
        leftTime = TOTAL_TIME;
        updateCountDownText();
    }
    public void updateCountDownText()
    {
        int second = (int)(leftTime/1000)%60;
        Time.setText(""+second);
    }
    public void pauseTimer()
    {
        countDownTimer.cancel();
        timerContinue = false;
    }
}
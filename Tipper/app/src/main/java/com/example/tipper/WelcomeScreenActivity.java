package com.example.tipper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

/**
 * Author Michał Wadas s20495
 */

public class WelcomeScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button quizButton = findViewById(R.id.button4);

        button1.setOnClickListener(view -> {
            Intent intent = new Intent(WelcomeScreenActivity.this, BMIActivity.class);
            startActivity(intent);
        });

        button2.setOnClickListener(view -> {
            Intent intent = new Intent(WelcomeScreenActivity.this, CaloriesCalcActivity.class);
            startActivity(intent);
        });
        button3.setOnClickListener(view -> {

            Intent intent = new Intent(WelcomeScreenActivity.this, ActivityRecipe.class);
            startActivity(intent);
        });

        quizButton.setOnClickListener(view -> {

            Intent intent = new Intent(WelcomeScreenActivity.this, QuizActivity.class);
            startActivity(intent);
        });


    }

}
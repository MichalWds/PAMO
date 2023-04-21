package com.example.tipper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;

import android.widget.RadioButton;
import android.widget.Toast;

import com.example.tipper.databinding.ActivityQuizBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Author Michał Wadas s20495
 */

public class QuizActivity extends AppCompatActivity {

    private ActivityQuizBinding binding;
    private JSONArray quizData;
    private int questionIndex = 0;
    private int correctAnswers = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Load JSON data
        loadQuizData();

        // question displayer
        displayQuestion(questionIndex);

        // "Check Answer" button
        binding.contentScrolling.submitAnswerButton.setOnClickListener(view -> checkAnswerAndProceed());
    }

    private void loadQuizData() {
        try {//Loading data from json file
            InputStream inputStream = getAssets().open("quiz.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String jsonData = new String(buffer, StandardCharsets.UTF_8);

            quizData = new JSONArray(jsonData);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    private void displayQuestion(int questionIndex) {
        try {
            JSONObject questionData = quizData.getJSONObject(questionIndex);
            binding.contentScrolling.questionTextView.setText(questionData.getString("question"));

            JSONObject answersData = questionData.getJSONObject("answers");
            binding.contentScrolling.answerA.setText(answersData.getString("a"));
            binding.contentScrolling.answerB.setText(answersData.getString("b"));
            binding.contentScrolling.answerC.setText(answersData.getString("c"));
            binding.contentScrolling.answerD.setText(answersData.getString("d"));

            binding.contentScrolling.answerRadioGroup.clearCheck();

            // Update question number and button text
            binding.contentScrolling.questionNumberTextView.setText((questionIndex + 1) + "/" + quizData.length());
            if (questionIndex == quizData.length() - 1) {
                binding.contentScrolling.submitAnswerButton.setText("Sprawdź wynik");
            } else {
                binding.contentScrolling.submitAnswerButton.setText("Następne pytanie");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkAnswerAndProceed() {
        try {
            int selectedAnswerId = binding.contentScrolling.answerRadioGroup.getCheckedRadioButtonId();

            if (selectedAnswerId == -1) {
                // No answer selected
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
                return;
            }

            RadioButton selectedAnswer = findViewById(selectedAnswerId);
            String selectedAnswerText = selectedAnswer.getText().toString();

            JSONObject questionData = quizData.getJSONObject(questionIndex);
            String correctAnswer = questionData.getString("correctAnswer");

            if (selectedAnswerText.equalsIgnoreCase(correctAnswer)) {
                correctAnswers++;
            }

            questionIndex++;

            if (questionIndex < quizData.length()) {
                displayQuestion(questionIndex);
            } else {
                showQuizResults();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showQuizResults() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quiz Results");
        builder.setMessage("Your score: " + correctAnswers + "/" + quizData.length());

        builder.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                questionIndex = 0;
                correctAnswers = 0;
                displayQuestion(questionIndex);
            }
        });

        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
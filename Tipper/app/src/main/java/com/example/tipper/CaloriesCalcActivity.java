package com.example.tipper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;
/**
 * Author Michał Wadas s20495
 */

public class CaloriesCalcActivity extends AppCompatActivity {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private EditText ageEditText;
    private EditText weightEditText;
    private EditText heightEditText;
    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private Button calculateButton;
    private TextView resultTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calories_calc);
        ageEditText = findViewById(R.id.age);
        weightEditText = findViewById(R.id.weight);
        heightEditText = findViewById(R.id.height);
        genderRadioGroup = findViewById(R.id.gender);
        maleRadioButton = findViewById(R.id.male);
        femaleRadioButton = findViewById(R.id.female);
        calculateButton = findViewById(R.id.calculate);
        resultTextView = findViewById(R.id.calories_result);
        RadioGroup radioGroup = findViewById(R.id.gender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton radioButton = (RadioButton) radioGroup.findViewById(checkedId);
                if (radioButton != null) {
                    // hide the keyboard
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(radioButton.getWindowToken(), 0);
                }
            }
        });
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateCalories();
            }
        });
    }

    @SuppressLint("StringFormatInvalid")
    private void calculateCalories() {

        int age = Integer.parseInt(ageEditText.getText().toString());
        float weight = Float.parseFloat(weightEditText.getText().toString());
        int height = Integer.parseInt(heightEditText.getText().toString());
        boolean isMale = maleRadioButton.isChecked();

        // BMR calc
        double bmr;
        if (isMale) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }

        resultTextView.setText(decimalFormat.format(bmr));
    }
}
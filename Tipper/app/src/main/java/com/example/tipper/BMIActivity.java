package com.example.tipper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.Button;
import android.widget.EditText; // for bill amount input

import android.widget.TextView; // for displaying text

import com.example.tipper.inmemory.BMIResultData;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Author Michał Wadas s20495
 */
public class BMIActivity extends AppCompatActivity {
    private BMIResultData bmiResultData;

    private Button saveButton; // save button
    private Button chartView; // save button

    // currency and percent formatter objects
// formatter object for number formatting
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    private double weight = 0.0; // weight entered by the user
    private double height = 0.0; // height entered by the user
    private TextView weightTextView; // shows formatted bill amount
    private TextView heightTextView; // shows tip percentage

    private TextView totalTextView; // shows calculated total bill amount

    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call superclass onCreate
        setContentView(R.layout.activity_main); // inflate the GUI
        bmiResultData = BMIResultData.getBmiResultData();
        // get references to programmatically manipulated TextViews
        weightTextView = (TextView) findViewById(R.id.weightTextView);
        weightTextView.setFocusable(true);
        weightTextView.setFocusableInTouchMode(true);
        heightTextView = (TextView) findViewById(R.id.heightTextView);
        heightTextView.setFocusable(true);
        heightTextView.setFocusableInTouchMode(true);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        // get references to programmatically manipulated TextViews
        totalTextView = findViewById(R.id.totalTextView);
        totalTextView.setText(decimalFormat.format(0.0));
        // set amountEditText's TextWatcher
        EditText weightEditText =
                (EditText) findViewById(R.id.weightEditText);
        weightEditText.addTextChangedListener(weightEditTextWatcher);

        // set heightEditText's TextWatcher
        EditText heightEditText =
                (EditText) findViewById(R.id.heightEditText);
        heightEditText.addTextChangedListener(heightEditTextWatcher);
        // hide the splash screen after a delay

        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveBMIResult());

        chartView = findViewById(R.id.graph);
        chartView.setOnClickListener(v -> startBMIGraphActivity());
    }

    private void startBMIGraphActivity() {
        Intent intent = new Intent(this, BMIGraphActivity.class);
        startActivity(intent);
    }

    // Save the current BMI result to the list
    private void saveBMIResult() {
        double bmi = Double.parseDouble(totalTextView.getText().toString());

        bmiResultData.addBMIResult(bmi);
    }

    //calculate bmi
    private void calculateBMI() {

        double bmi =  (weight / ((height * height) / 10000));

        // display BMI formatted to two decimal places
        totalTextView.setText(decimalFormat.format(bmi));
    }



    // listener object for the EditText's text-changed events
    private final TextWatcher weightEditTextWatcher = new TextWatcher() {
        // called when the user modifies the bill amount
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get weight and display formatted value
                weight = Double.parseDouble(s.toString());
                weightTextView.setText(decimalFormat.format(weight));
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                weight = 0.0;
            }

            calculateBMI(); // update the BMI TextView
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) { }
    };
    // listener object for the EditText's text-changed events
    private final TextWatcher heightEditTextWatcher = new TextWatcher() {
        // called when the user modifies the height
        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get height and display formatted value
                height = Double.parseDouble(s.toString());
                heightTextView.setText(decimalFormat.format(height));
            }
            catch (NumberFormatException e) { // if s is empty or non-numeric
                height = 0.0;
            }

            calculateBMI(); // update the BMI TextView
        }

        @Override
        public void afterTextChanged(Editable s) { }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) { }
    };

    public static class BMIResult implements Serializable {
        public double bmi;
        public Date date;

        public BMIResult(double bmi, Date date) {
            this.bmi = bmi;
            this.date = date;
        }
    }


}

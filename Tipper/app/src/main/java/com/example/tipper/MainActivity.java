package com.example.tipper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText; // for bill amount input
import android.widget.Spinner;
import android.widget.TextView; // for displaying text

import java.text.NumberFormat; // for currency formatting
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    // currency and percent formatter objects
    private static final NumberFormat numberFormat =
            NumberFormat.getNumberInstance();

    private double weightAmount = 0.000; // bill amount entered by the user
    private double heightAmount = 0.000; // bill amount entered by the user
    private TextView amountTextView; // shows formatted bill amount
    private TextView amountTextView2; // shows formatted bill amount
    private TextView totalTextView; // shows calculated bmi  amount
    private TextView recipeTextView; // shows calculated bmi  amount

    private TextView bmiTextView;


    private int age = 0;
    private String gender = "Female";
    private String activityLevel = "Sedentary";
    private TextView caloriesTextView;


    // called when the activity is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // call superclass onCreate
        setContentView(R.layout.activity_main); // inflate the GUI

        // get references to programmatically manipulated TextViews
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        amountTextView2 = (TextView) findViewById(R.id.amountTextView2);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        totalTextView.setText(numberFormat.format(0.00));

        // set amountEditText's TextWatcher
        EditText amountEditText =
                (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        EditText amountEditText2 =
                (EditText) findViewById(R.id.amountEditText2);
        amountEditText2.addTextChangedListener(amountEditTextWatcher2);

        // Initialize EditText for age input
        EditText ageEditText = (EditText) findViewById(R.id.ageEditText);
        ageEditText.addTextChangedListener(ageEditTextWatcher);

// Initialize Spinner for gender selection
        Spinner genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        genderSpinner.setOnItemSelectedListener(genderItemSelectedListener);

// Initialize Spinner for activity level selection
        Spinner activitySpinner = (Spinner) findViewById(R.id.activitySpinner);
        activitySpinner.setOnItemSelectedListener(activityItemSelectedListener);

        // Initialize TextView for displaying calories
        caloriesTextView = (TextView) findViewById(R.id.caloriesTextView);
        caloriesTextView.setText(numberFormat.format(0.00));

        bmiTextView = (TextView) findViewById(R.id.bmiTextView);


    }

    // calculate and display tip and total amounts
    private void calculate() {
        // format percent and display in percentTextView

        // calculate the tip and total
        double total = weightAmount / (heightAmount / 100.00 * heightAmount / 100.00);

        // display tip and total formatted as currency
        totalTextView.setText(numberFormat.format(total));



        // Calculate calories using the Benedict-Harris formula
        double bmr;
        if (gender.equals("Female")) {
            bmr = 655 + (9.6 * weightAmount) + (1.8 * heightAmount) - (4.7 * age);
        } else {
            bmr = 66 + (13.7 * weightAmount) + (5 * heightAmount) - (6.8 * age);
        }

        double calories = bmr * getActivityLevelFactor(activityLevel);
        caloriesTextView.setText(numberFormat.format(calories));

        // Display recipe recommendation
        String recipe = getRecipeRecommendation();
        TextView recipeTextView = (TextView) findViewById(R.id.recipeTextView);
        recipeTextView.setText(getResources().getString(R.string.recipe_label) + " " + recipe);
    }

    private double getActivityLevelFactor(String activityLevel) {
        switch (activityLevel) {
            case "Sedentary":
                return 1.2;
            case "Lightly active":
                return 1.375;
            case "Moderately active":
                return 1.55;
            case "Very active":
                return 1.725;
            case "Extra active":
                return 1.9;
            default:
                return 1.0;
        }
    }

    private String getRecipeRecommendation() {
        // Here you can add more recipes
        String[] recipes = {
                "Przepis 1: Sałatka grecka",
                "Przepis 2: Spaghetti bolognese"
        };

        // Choose a random recipe from the list
        Random random = new Random();
        int index = random.nextInt(recipes.length);
        return recipes[index];
    }

    // listener object for the EditText's text-changed events
    private final TextWatcher amountEditTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get weight amount and display currency formatted value
                weightAmount = Double.parseDouble(s.toString());
                amountTextView.setText(numberFormat.format(weightAmount));
            } catch (NumberFormatException e) { // if s is empty or non-numeric
                amountTextView.setText("");
            }
            calculate();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) {
        }
    };

    private final TextWatcher amountEditTextWatcher2 = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start,
                                  int before, int count) {

            try { // get height amount and display currency formatted value
                heightAmount = Double.parseDouble(s.toString());
                amountTextView2.setText(numberFormat.format(heightAmount));
            } catch (NumberFormatException e) { // if s is empty or non-numeric
                amountTextView2.setText("");
            }

            calculate();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(
                CharSequence s, int start, int count, int after) {
        }
    };

    private final AdapterView.OnItemSelectedListener genderItemSelectedListener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    gender = (String) parent.getItemAtPosition(position);
                    calculate();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // No action needed
                }
            };

    private final AdapterView.OnItemSelectedListener activityItemSelectedListener =
            new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    activityLevel = (String) parent.getItemAtPosition(position);
                    calculate();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // No action needed
                }
            };
    private final TextWatcher ageEditTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                age = Integer.parseInt(s.toString());
            } catch (NumberFormatException e) {
                age = 0;
            }
            calculate();
        }

        @Override
        public void afterTextChanged(Editable s) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
    };
}

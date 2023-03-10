package com.example.tipper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.TextView; // for displaying text

import java.text.NumberFormat; // for currency formatting

public class MainActivity extends AppCompatActivity {

    // currency and percent formatter objects
    private static final NumberFormat numberFormat =
            NumberFormat.getNumberInstance();

    private double weightAmount = 0.000; // bill amount entered by the user
    private double heightAmount = 0.000; // bill amount entered by the user
    private TextView amountTextView; // shows formatted bill amount
    private TextView amountTextView2; // shows formatted bill amount
    private TextView totalTextView; // shows calculated bmi  amount

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
    }

    // calculate and display tip and total amounts
    private void calculate() {
        // format percent and display in percentTextView

        // calculate the tip and total
        double total = weightAmount / (heightAmount / 100.00 * heightAmount / 100.00);

        // display tip and total formatted as currency
        totalTextView.setText(numberFormat.format(total));
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
}

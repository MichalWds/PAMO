package com.example.tipper

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

/**
 * Author Michał Wadas s20495
 */
class CaloriesCalcActivity : AppCompatActivity() {
    private var ageEditText: EditText? = null
    private var weightEditText: EditText? = null
    private var heightEditText: EditText? = null
    private var genderRadioGroup: RadioGroup? = null
    private var maleRadioButton: RadioButton? = null
    private var femaleRadioButton: RadioButton? = null
    private var calculateButton: Button? = null
    private var resultTextView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calories_calc)
        ageEditText = findViewById(R.id.age)
        weightEditText = findViewById(R.id.weight)
        heightEditText = findViewById(R.id.height)
        genderRadioGroup = findViewById(R.id.gender)
        maleRadioButton = findViewById(R.id.male)
        femaleRadioButton = findViewById(R.id.female)
        calculateButton = findViewById(R.id.calculate)
        resultTextView = findViewById(R.id.calories_result)
        val radioGroup = findViewById<RadioGroup>(R.id.gender)
        radioGroup.setOnCheckedChangeListener { radioGroup, checkedId ->
            val radioButton = radioGroup.findViewById<View>(checkedId) as? RadioButton
            radioButton?.let {
                // hide the keyboard
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            }
        }
        calculateButton?.setOnClickListener { calculateCalories() }
    }

    @SuppressLint("StringFormatInvalid")
    private fun calculateCalories() {
        val age = ageEditText?.text.toString().toInt()
        val weight = weightEditText?.text.toString().toFloat()
        val height = heightEditText?.text.toString().toInt()
        val isMale = maleRadioButton?.isChecked == true

        // BMR calc
        val bmr: Double
        bmr = if (isMale) {
            88.362 + 13.397 * weight + 4.799 * height - 5.677 * age
        } else {
            447.593 + 9.247 * weight + 3.098 * height - 4.330 * age
        }
        resultTextView?.text = decimalFormat.format(bmr)
    }

    companion object {
        private val decimalFormat = DecimalFormat("#.##")
    }
}
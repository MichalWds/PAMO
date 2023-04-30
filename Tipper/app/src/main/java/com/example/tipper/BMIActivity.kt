package com.example.tipper

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tipper.inmemory.BMIResultData
import java.io.Serializable
import java.text.DecimalFormat
import java.util.*

/**
 * Author Michał Wadas s20495
 */
class BMIActivity : AppCompatActivity() {
    private var bmiResultData: BMIResultData? = null
    private var saveButton: Button? = null
    private var chartView: Button? = null
    private var weight = 0.0
    private var height = 0.0
    private var weightTextView: TextView? = null
    private var heightTextView: TextView? = null
    private lateinit var totalTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bmiResultData = BMIResultData.bmiResultData

        weightTextView = findViewById<TextView>(R.id.weightTextView).apply {
            isFocusable = true
            isFocusableInTouchMode = true
        }
        heightTextView = findViewById<TextView>(R.id.heightTextView).apply {
            isFocusable = true
            isFocusableInTouchMode = true
        }
        totalTextView = findViewById(R.id.totalTextView)

        totalTextView.text = decimalFormat.format(0.0)

        val weightEditText = findViewById<EditText>(R.id.weightEditText)
        weightEditText.addTextChangedListener(weightEditTextWatcher)

        val heightEditText = findViewById<EditText>(R.id.heightEditText)
        heightEditText.addTextChangedListener(heightEditTextWatcher)

        saveButton = findViewById(R.id.saveButton)
        saveButton?.setOnClickListener { saveBMIResult() }

        chartView = findViewById(R.id.graph)
        chartView?.setOnClickListener { startBMIGraphActivity() }
    }

    private fun startBMIGraphActivity() {
        val intent = Intent(this, BMIGraphActivity::class.java)
        startActivity(intent)
    }

    private fun saveBMIResult() {
        val bmi = totalTextView.text.toString().toDouble()
        bmiResultData?.addBMIResult(bmi)
    }

    private fun calculateBMI() {
        val bmi = weight / (height * height / 10000)
        totalTextView.text = decimalFormat.format(bmi)
    }

    private val weightEditTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            try {
                weight = s.toString().toDouble()
                weightTextView?.text = decimalFormat.format(weight)
            } catch (e: NumberFormatException) {
                weight = 0.0
            }
            calculateBMI()
        }

        override fun afterTextChanged(s: Editable) {}
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    }

    private val heightEditTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            try {
                height = s.toString().toDouble()
                heightTextView?.text = decimalFormat.format(height)
            } catch (e: NumberFormatException) {
                height = 0.0
            }
            calculateBMI()
        }

        override fun afterTextChanged(s: Editable) {}
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    }

    class BMIResult(var bmi: Double, var date: Date) : Serializable

    companion object {
        private val decimalFormat = DecimalFormat("#.##")
    }
}

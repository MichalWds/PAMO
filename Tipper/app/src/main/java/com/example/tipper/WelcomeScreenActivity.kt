package com.example.tipper

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * Author Michał Wadas s20495
 */
class WelcomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        val button1 = findViewById<Button>(R.id.button1)
        val button2 = findViewById<Button>(R.id.button2)
        val button3 = findViewById<Button>(R.id.button3)
        val quizButton = findViewById<Button>(R.id.button4)
        button1.setOnClickListener { view: View? ->
            val intent = Intent(this@WelcomeScreenActivity, BMIActivity::class.java)
            startActivity(intent)
        }
        button2.setOnClickListener { view: View? ->
            val intent = Intent(this@WelcomeScreenActivity, CaloriesCalcActivity::class.java)
            startActivity(intent)
        }
        button3.setOnClickListener { view: View? ->
            val intent = Intent(this@WelcomeScreenActivity, ActivityRecipe::class.java)
            startActivity(intent)
        }
        quizButton.setOnClickListener { view: View? ->
            val intent = Intent(this@WelcomeScreenActivity, QuizActivity::class.java)
            startActivity(intent)
        }
    }
}
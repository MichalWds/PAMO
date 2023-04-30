package com.example.tipper

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tipper.databinding.ActivityQuizBinding
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 * Author Michał Wadas s20495
 */
class QuizActivity : AppCompatActivity() {
    private var binding: ActivityQuizBinding? = null
    private var quizData: JSONArray? = null
    private var questionIndex = 0
    private var correctAnswers = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // Load JSON data
        loadQuizData()

        // question displayer
        displayQuestion(questionIndex)

        // "Check Answer" button
        binding!!.contentScrolling.submitAnswerButton!!.setOnClickListener { view: View? -> checkAnswerAndProceed() }
    }

    private fun loadQuizData() {
        try { //Loading data from json file
            val inputStream = assets.open("quiz.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            val jsonData = String(buffer, StandardCharsets.UTF_8)
            quizData = JSONArray(jsonData)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun displayQuestion(questionIndex: Int) {
        try {
            val questionData = quizData!!.getJSONObject(questionIndex)
            binding!!.contentScrolling.questionTextView!!.text = questionData.getString("question")
            val answersData = questionData.getJSONObject("answers")
            binding!!.contentScrolling.answerA!!.text = answersData.getString("a")
            binding!!.contentScrolling.answerB!!.text = answersData.getString("b")
            binding!!.contentScrolling.answerC!!.text = answersData.getString("c")
            binding!!.contentScrolling.answerD!!.text = answersData.getString("d")
            binding!!.contentScrolling.answerRadioGroup!!.clearCheck()

            // Update question number and button text
            binding!!.contentScrolling.questionNumberTextView!!.text = (questionIndex + 1).toString() + "/" + quizData!!.length()
            if (questionIndex == quizData!!.length() - 1) {
                binding!!.contentScrolling.submitAnswerButton!!.text = "Sprawdź wynik"
            } else {
                binding!!.contentScrolling.submitAnswerButton!!.text = "Następne pytanie"
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun checkAnswerAndProceed() {
        try {
            val selectedAnswerId = binding!!.contentScrolling.answerRadioGroup!!.checkedRadioButtonId
            if (selectedAnswerId == -1) {
                // No answer selected
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
                return
            }
            val selectedAnswer = findViewById<RadioButton>(selectedAnswerId)
            val selectedAnswerText = selectedAnswer.text.toString()
            val questionData = quizData!!.getJSONObject(questionIndex)
            val correctAnswer = questionData.getString("correctAnswer")
            if (selectedAnswerText.equals(correctAnswer, ignoreCase = true)) {
                correctAnswers++
            }
            questionIndex++
            if (questionIndex < quizData!!.length()) {
                displayQuestion(questionIndex)
            } else {
                showQuizResults()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun showQuizResults() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quiz Results")
        builder.setMessage("Your score: " + correctAnswers + "/" + quizData!!.length())
        builder.setPositiveButton("Try Again") { dialog, which ->
            questionIndex = 0
            correctAnswers = 0
            displayQuestion(questionIndex)
        }
        builder.setNegativeButton("Close") { dialog, which -> finish() }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}
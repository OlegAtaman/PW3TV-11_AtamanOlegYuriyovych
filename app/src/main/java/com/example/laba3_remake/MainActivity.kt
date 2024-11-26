package com.example.laba3_remake

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    fun pd_calc(p: Double, p_c: Double, sigma: Double): Double {
        return 1 / (sigma * sqrt(2 * PI)) * exp(-(p - p_c).pow(2) / (2 * sigma.pow(2)))
    }

    fun integrate(
        a: Double,
        b: Double,
        n: Int,
        sigma: Double,
        p_c: Double
    ): Double {
        val h = (b - a) / n
        var result = 0.5 * (pd_calc(a, p_c, sigma) + pd_calc(b, p_c, sigma))

        for (i in 1 until n) {
            result += pd_calc(a + i * h, p_c, sigma)  // додаємо значення функції у внутрішніх точках
        }

        result *= h
        return result
    }

    // Declare UI components
    private lateinit var editTextA: EditText
    private lateinit var editTextB: EditText
    private lateinit var editTextC: EditText
    private lateinit var buttonCalculate: Button
    private lateinit var textViewIncome: TextView
    private lateinit var textViewPenalty: TextView
    private lateinit var textViewAnswer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to the updated layout
        setContentView(R.layout.activity_main)

        // Initialize UI components
        editTextA = findViewById(R.id.editTextA)
        editTextB = findViewById(R.id.editTextB)
        editTextC = findViewById(R.id.editTextC)
        buttonCalculate = findViewById(R.id.buttonCalculate)
        textViewIncome = findViewById(R.id.textViewIncome)
        textViewPenalty = findViewById(R.id.textViewPenalty)
        textViewAnswer = findViewById(R.id.textViewAnswer)

        // Set click listener for the calculate button
        buttonCalculate.setOnClickListener {
            calculateResult()
        }
    }

    // Function to perform the calculation
    private fun calculateResult() {
        // Retrieve and validate input values
        val aText = editTextA.text.toString()
        val bText = editTextB.text.toString()
        val cText = editTextC.text.toString()

        // Convert input strings to Double
        val a = aText.toDouble()
        val b = bText.toDouble()
        val c = cText.toDouble()

        val lower = a - a * 0.05
        val upper = a + a * 0.05

        val percent = integrate(lower, upper, 5000, b, a)
        val income = percent * a * c * 24 * 1000
        val penalty = (1 - percent) * a * c * 24 * 1000

        val answer = income - penalty


        // Display the result
        textViewIncome.text = "Прибуток: $income грн"
        textViewPenalty.text = "Штраф: $penalty грн"
        textViewAnswer.text = "Чистий дохід: $answer грн"
    }
}
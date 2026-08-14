package com.example.calculatorkotlin

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    val operations = Operation()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun onAdd(view: View) {

    }

    fun onSubtraction(view: View) {

    }

    fun onMultiplication(view: View) {

    }

    fun onDivision(view: View) {

    }

    fun onResult(view: View) {
        val value1 = findViewById<EditText>(R.id.value1)
        val value2 = findViewById<EditText>(R.id.value2)

        val number1: Double = value1.text.toString().toDouble()
        val number2: Double = value2.text.toString().toDouble()

        val addResult = operations.add(number1,number2)

    }

    fun onClear(view: View) {
        val value1 = findViewById<EditText>(R.id.value1)
        val value2 = findViewById<EditText>(R.id.value2)
        val symbol = findViewById<EditText>(R.id.symbolId)

        value1.setText("")
        value2.setText("")
        symbol.setText("")
    }
}
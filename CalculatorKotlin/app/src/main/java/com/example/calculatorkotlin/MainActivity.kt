package com.example.calculatorkotlin

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
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

    fun onCalculatorOperation(view: View){

        if(view.id == R.id.addId){
            operations.setAddFlag(true);
        } else if (view.id == R.id.subsId) {
            operations.setSubtractionFlag(true);
        } else if (view.id == R.id.multiplicationId) {
            operations.setMultiplicationFlag(true);
        } else if (view.id == R.id.divisionId) {
            operations.setDivisionFlag(true);
        }
    }

    fun onResult(view: View) {
        val value1 = findViewById<EditText>(R.id.value1)
        val value2 = findViewById<EditText>(R.id.value2)
        val resultId = findViewById<TextView>(R.id.resultId)

        val number1: Double = value1.text.toString().toDouble()
        val number2: Double = value2.text.toString().toDouble()

        val addResult = operations.add(number1,number2)

        resultId.setText(addResult.toString())

        if(operations.getAddFlag()){
            val addResult = operations.add(number1,number2)
            resultId.setText(addResult.toString())
        } else if (operations.getSubtractionFlag()) {
            val addResult = operations.subtraction(number1,number2)
            resultId.setText(addResult.toString())
        } else if (operations.getMultiplicationFlag()) {
            val addResult = operations.multiplication(number1,number2)
            resultId.setText(addResult.toString())
        } else if (operations.getDivisionFlag()) {
            val addResult = operations.division(number1,number2)
            resultId.setText(addResult.toString())
        }

        onClear();

    }

    fun onClear() {
        val value1 = findViewById<EditText>(R.id.value1)
        val value2 = findViewById<EditText>(R.id.value2)
        val symbol = findViewById<EditText>(R.id.symbolId)
        val resultId = findViewById<TextView>(R.id.resultId)

        value1.setText("")
        value2.setText("")
        symbol.setText("")
        resultId.setText("")

        operations.setAddFlag(false);
        operations.setSubtractionFlag(false);
        operations.setMultiplicationFlag(false);
        operations.setDivisionFlag(false);
    }
}
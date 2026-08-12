package com.project.hellowordkotlin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
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

    fun clickOnMe(view: View) {
        Toast.makeText(applicationContext, "Hello World Button!!", Toast.LENGTH_SHORT).show()
    }

    fun onRegister(view: View) {
        val myTextView = findViewById<TextView>(R.id.emailId)
        val myEmailTextView = findViewById<TextView>(R.id.emailTextID)

        val emailAdress:String = myTextView.text.toString()
        Toast.makeText(applicationContext, "Register Button!!\n" + emailAdress, Toast.LENGTH_SHORT).show()

        myEmailTextView.text = myTextView.text.toString()
    }

    fun onLogin(view: View) {
        Toast.makeText(applicationContext, "Login Button!!", Toast.LENGTH_SHORT).show()
    }
}
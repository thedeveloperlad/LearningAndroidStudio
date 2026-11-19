package com.project.addwordsinalist

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

    fun onSave(view: View) {
        val myTitleTextView = findViewById<TextView>(R.id.titleId)
        val myDescriptionTextView = findViewById<TextView>(R.id.descriptionId)

        val title:String = myTitleTextView.text.toString()
        val description:String = myDescriptionTextView.text.toString()

        Toast.makeText(applicationContext, "Save Button!!\n" + title, Toast.LENGTH_SHORT).show()

        // Note(title, description, 0)

        // myEmailTextView.text = myTextView.text.toString()
    }
}
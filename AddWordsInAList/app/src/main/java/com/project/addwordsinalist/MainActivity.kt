package com.project.addwordsinalist

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    lateinit var arrayAdapter: ArrayAdapter<*>
    private val notes = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Add initial item(s)
        // notes.add(Note("Sample Title", "Sample Description", 0))

        listView = findViewById(R.id.word_list)
        arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notes)
        listView.adapter = arrayAdapter
    }

    fun onSave(view: View) {
        val myTitleTextView = findViewById<TextView>(R.id.titleId)
        val myDescriptionTextView = findViewById<TextView>(R.id.descriptionId)

        val title:String = myTitleTextView.text.toString()
        val description:String = myDescriptionTextView.text.toString()

        // Toast.makeText(applicationContext, "Save Button!!\n" + title, Toast.LENGTH_SHORT).show()

        val newNote = Note(title, description, notes.size)
        notes.add(newNote)
        arrayAdapter.notifyDataSetChanged()
    }
}
package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class NewActivity : AppCompatActivity() {

    private lateinit var welcomeText: TextView
    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_new)
//        welcomeText = findViewById(R.id.welcomeText)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)
        welcomeText = findViewById(R.id.welcomeText)
        backButton = findViewById(R.id.backButton)

        // Open new activity on button click
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
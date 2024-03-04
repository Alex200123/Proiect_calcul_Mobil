package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LocationsMenuActivity : AppCompatActivity() {

    private lateinit var welcomeText: TextView
    private lateinit var backButton: Button
    private lateinit var addButton: Button
    private lateinit var viewButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_new)
//        welcomeText = findViewById(R.id.welcomeText)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations_menu)
        welcomeText = findViewById(R.id.welcomeText)
        backButton = findViewById(R.id.backButton)
        addButton = findViewById(R.id.AddLocationButton)
        viewButton = findViewById(R.id.ViewLocationsButton)
        // Open new activity on button click
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        addButton.setOnClickListener {
            val intent = Intent(this, AddLocationActivity::class.java)
            startActivity(intent)
        }

        viewButton.setOnClickListener {
            val intent = Intent(this, ViewLocationsActivity::class.java)
            startActivity(intent)
        }
    }
}
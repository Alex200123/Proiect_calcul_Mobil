package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddLocationActivity : AppCompatActivity() {

    private lateinit var backButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_location)


        backButton = findViewById(R.id.backButton)

        // Open new activity on button click
        backButton.setOnClickListener {
            val intent = Intent(this, LocationsMenuActivity::class.java)
            startActivity(intent)
        }


    }
}
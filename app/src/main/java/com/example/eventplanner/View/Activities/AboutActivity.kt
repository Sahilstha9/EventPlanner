package com.example.eventplanner.View.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.eventplanner.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        var about = findViewById<TextView>(R.id.about)

        about.text = "This is a Event Planner Application\nThis is Level 2 Application"


    }
}
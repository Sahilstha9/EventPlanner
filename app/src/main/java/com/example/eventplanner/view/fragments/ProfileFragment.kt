package com.example.eventplanner.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import com.example.eventplanner.R
import com.example.eventplanner.view.activities.AboutActivity

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<LinearLayout>(R.id.about).setOnClickListener{
            val intent = Intent(context, AboutActivity::class.java)
            startActivity(intent)
        }
    }
}
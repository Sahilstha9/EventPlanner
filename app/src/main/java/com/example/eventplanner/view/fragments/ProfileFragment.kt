package com.example.eventplanner.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.eventplanner.R
import com.example.eventplanner.modal.AuthenticationModal
import com.example.eventplanner.view.activities.AboutActivity
import com.example.eventplanner.view.activities.SignInActivity

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val db = AuthenticationModal

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val auth = AuthenticationModal

        view.findViewById<TextView>(R.id.displayName).text = auth.getUser().value?.displayName
        view.findViewById<LinearLayout>(R.id.about).setOnClickListener{
            val intent = Intent(context, AboutActivity::class.java)
            startActivity(intent)
        }

        view.findViewById<LinearLayout>(R.id.logout).setOnClickListener{
            db.logOutUser()
            val intent = Intent(this.context, SignInActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
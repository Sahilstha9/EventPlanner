package com.example.eventplanner.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eventplanner.databinding.ActivitySignInBinding
import com.example.eventplanner.viewModel.SignInViewModel
import com.example.eventplanner.viewModel.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            viewModel.loginUser(binding.emailEt, binding.passET, this)
        }

        viewModel.user.observe(this, Observer {
            if(it != null){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun onStart() {
        super.onStart()

        if (viewModel.user.value != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
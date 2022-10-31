package com.example.eventplanner.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.eventplanner.databinding.ActivitySignInBinding
import com.example.eventplanner.modal.CategoryDatabase
import com.example.eventplanner.modal.EventDatabase
import com.example.eventplanner.viewModel.SignInViewModel

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
            CategoryDatabase.mCategoryList.value = null
            EventDatabase.mEventList.value = null
        }

        viewModel.user.observe(this) {
            if (it != null) {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
            }
        }
    }

    override fun onBackPressed() {

    }

    override fun onStart() {
        super.onStart()

        if (viewModel.user.value != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
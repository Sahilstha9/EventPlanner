package com.example.eventplanner.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.eventplanner.databinding.ActivitySignInBinding
import com.example.eventplanner.repository.CategoryRepository
import com.example.eventplanner.repository.EventRepository
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
            CategoryRepository.reInitList()
            EventRepository.reInitList()
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

    /**
     * goes to main activity if the user is already logged in
     */
    override fun onStart() {
        super.onStart()

        if (viewModel.user.value != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
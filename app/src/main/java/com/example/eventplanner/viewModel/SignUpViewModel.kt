package com.example.eventplanner.viewModel

import android.content.Context
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventplanner.modal.AuthenticationModal
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseUser

class SignUpViewModel : ViewModel() {

    private val db = AuthenticationModal
    val user : MutableLiveData<FirebaseUser> = db.getUser()

    fun registerNewUser(emailText : TextInputEditText, passText : TextInputEditText, confirmPass: TextInputEditText, name: TextInputEditText, context: Context){
        var email = ""
        var pass = ""

        if(emailText.text!!.isEmpty()){
            emailText.error = "Email Field can not be empty"
        }
        else if(!validEmail(email)){
            emailText.error = "Invalid Email Format"
        }
        else{
            email = emailText.text.toString()
        }

        if(passText.text!!.isEmpty()){
            passText.error = "Passwords cannot be empty"
        }
        else if(confirmPass.text!!.isEmpty()){
            confirmPass.error = "Confirm Password can not be empty"
        }
        else {
            if (passText.text.toString() == confirmPass.text.toString()) {
                pass = passText.text.toString()
            } else {
                passText.error = "The passwords do not match"
            }
        }

        if(email != "" && pass != ""){
            db.registerNewUser(email, pass, name.text.toString(), context)
        }
    }

    private fun validEmail(email : String): Boolean {
        val regex : Regex = Regex("^[a-zA-Z0-9.!#\$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\$")
        if(!email.matches(regex)){
            return true
        }
        return false
    }
}
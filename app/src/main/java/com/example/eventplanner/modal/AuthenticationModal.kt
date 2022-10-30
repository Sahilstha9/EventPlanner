package com.example.eventplanner.modal

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest

object AuthenticationModal {
    const val TAG = "AuthenticationModal"
    private var currentUser = MutableLiveData<FirebaseUser>(getDatabaseRef().currentUser)

    fun getUser(): MutableLiveData<FirebaseUser> {
        return currentUser
    }

    private fun getDatabaseRef(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    fun updateProfile(name : String){
        val user = userProfileChangeRequest {
            displayName = name
        }
        getDatabaseRef().currentUser!!.updateProfile(user)
    }

    fun registerNewUser(email : String, pass : String, name: String, context: Context){
        getDatabaseRef().createUserWithEmailAndPassword(email, pass).addOnCompleteListener { it ->
            if (it.isSuccessful) {
                currentUser.value = getDatabaseRef().currentUser
                currentUser.postValue(currentUser.value)
                val user = userProfileChangeRequest {
                    displayName = name
                }
                it.result.user?.updateProfile(user)?.addOnCompleteListener{task ->
                    if(task.isSuccessful){
                        Log.i(TAG, "User profile update Sucessful")
                    }
                }
            } else {
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                currentUser.postValue(null)
            }
        }
    }

    fun logOutUser(){
        getDatabaseRef().signOut()
        currentUser.value = null
        currentUser.postValue(null)
    }

    fun loginUser(email : String, pass : String, context: Context){
        getDatabaseRef().signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                currentUser.value = getDatabaseRef().currentUser
                currentUser.postValue(currentUser.value)
            } else {
                Toast.makeText(context, it.exception.toString(), Toast.LENGTH_SHORT).show()
                currentUser.postValue(null)
            }
        }
    }
}
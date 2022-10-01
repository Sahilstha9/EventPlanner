package com.example.eventplanner.Modal

import android.util.Log
import com.example.eventplanner.ViewModel.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EventDatabase {
    private val db = FirebaseDatabase.getInstance().getReference("Events")
    private val TAG = "EventDatabase"

    public fun createEvent(event : Event){
        val id = db.push().key!!

        db.child(id).setValue(event).addOnSuccessListener {
            Log.i(TAG, "1 document added to Event Collection")
        }
    }

    public fun getEvents(){
        val postListener = object : ValueEventListener {
            override fun onDataChange(events: DataSnapshot) {
                // Get Post object and use the values to update the UI
                val event = events.getValue()
                // ...
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        db.addValueEventListener(postListener)
    }

    public fun updateEvent(id : String, event : Event){

    }
}
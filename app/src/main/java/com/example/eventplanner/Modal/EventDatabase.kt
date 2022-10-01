package com.example.eventplanner.Modal

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.eventplanner.viewModel.parcels.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class EventDatabase {
    val TAG = "EventDatabase"
    private val db = FirebaseDatabase.getInstance().getReference("Events")
    public var eventList : MutableList<Event> = mutableListOf()

    fun createEvent(event : Event){
        event.id = db.push().key!!

        db.child(event.id).setValue(event).addOnSuccessListener {
            Log.i(TAG, "1 document added to Event Collection")
        }.addOnFailureListener{
            Log.i(TAG, "Failure on adding events")
        }
    }

    fun getEvents() : MutableList<Event>{
        db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(events: DataSnapshot) {
                if (events.exists()){
                    eventList.clear()
                    for (e in events.children){
                        val event = e.getValue(Event::class.java)
                        eventList.add(event!!)
                        Log.i(TAG, event.category)
                    }
                    Log.i(TAG, eventList.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return eventList
    }

    fun updateEvent(event : Event){
        db.child(event.id).setValue(event).addOnSuccessListener {
            Log.i(TAG, "${event.id} Record Modified")
        }.addOnCanceledListener {
            Log.i(TAG, "${event.id} Record failed to be modified")
        }
    }

    fun deleteEvent(event : Event){
        db.child(event.id).removeValue().addOnSuccessListener {
            Log.i(TAG, "${event.id} Record Deleted")
        }.addOnCanceledListener {
            Log.i(TAG, "${event.id} Record Failed to be deleted")
        }
    }
}
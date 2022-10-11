package com.example.eventplanner.Modal

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.Observable

object EventDatabase : Observable(){
    val TAG = "EventDatabase"
    private var mEventList : MutableList<Event> = mutableListOf()
    fun getDatabaseRef() : DatabaseReference{
        return FirebaseDatabase.getInstance().getReference("Events")
    }


    fun createEvent(event : Event){
        event.id = getDatabaseRef().push().key!!

        getDatabaseRef().child(event.id).setValue(event).addOnSuccessListener {
            Log.i(TAG, "1 document added to Event Collection")
        }.addOnFailureListener{
            Log.i(TAG, "Failure on adding events")
        }
    }

    fun getEvents(){
        getDatabaseRef().addValueEventListener(object : ValueEventListener{
            override fun onDataChange(events: DataSnapshot) {
                val data : ArrayList<Event> = ArrayList()
                if (events.exists()){
                    mEventList.clear()
                    for (e in events.children){
                        val event = e.getValue(Event::class.java)
                        data.add(event!!)
                        Log.i(TAG, event.category)
                    }
                    mEventList = data
                    Log.i(TAG, "data updated, there are " + mEventList!!.size + "e entrees in the cache")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getEventsOnce() : MutableList<Event>{
        getDatabaseRef().get().addOnSuccessListener {
            //Log.i(TAG, "Got value ${it.value}")
            if (it.exists()){
                var eventList = mutableListOf<Event>()
                for(i in it.children){
                    var event = i.getValue(Event::class.java)!!
                    eventList.add(event)
                }
                mEventList = eventList
                Log.i(TAG, "Got value $eventList")
            }
        }.addOnFailureListener{
            Log.e(TAG, "Error getting data", it)
        }
        return mEventList
    }

    fun updateEvent(event : Event){
        getDatabaseRef().child(event.id).setValue(event).addOnSuccessListener {
            Log.i(TAG, "${event.id} Record Modified")
        }.addOnCanceledListener {
            Log.i(TAG, "${event.id} Record failed to be modified")
        }
    }

    fun deleteEvent(event : Event){
        getDatabaseRef().child(event.id).removeValue().addOnSuccessListener {
            Log.i(TAG, "${event.id} Record Deleted")
        }.addOnCanceledListener {
            Log.i(TAG, "${event.id} Record Failed to be deleted")
        }
    }
}
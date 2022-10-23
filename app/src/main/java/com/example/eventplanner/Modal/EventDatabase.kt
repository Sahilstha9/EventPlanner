package com.example.eventplanner.Modal

import android.net.Uri
import android.util.Log
import android.view.View
import com.example.eventplanner.viewModel.parcels.Event
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

object EventDatabase : Observable(){
    val TAG = "EventDatabase"
    private var imageDB = ImageModal
    private var mEventList : MutableList<Event> = mutableListOf()

    fun getDatabaseRef() : DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("Events")
    }

    fun createEvent(event : Event, view: View, img : Uri?){
        event.id = getDatabaseRef().push().key!!
        event.imageLoc = event.id
        imageDB.uploadImage(img, event.id)
        getDatabaseRef().child(event.id).setValue(event).addOnSuccessListener {
            Snackbar.make(view, "Event has been successfully added", Snackbar.LENGTH_LONG).show()
            Log.i(TAG, "1 document added to Event Collection")
        }.addOnFailureListener{
            Log.i(TAG, "Failure on adding events")
        }
    }

    fun getEventsOnce() : MutableList<Event>{
        getDatabaseRef().get().addOnSuccessListener {
            //Log.i(TAG, "Got value ${it.value}")
            if (it.exists()){
                val eventList = mutableListOf<Event>()
                for(i in it.children){
                    val event = i.getValue(Event::class.java)!!
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

    fun updateEvent(event : Event, view: View){
        getDatabaseRef().child(event.id).setValue(event).addOnSuccessListener {
            Snackbar.make(view, "Event Record Successfully updated", Snackbar.LENGTH_LONG).show()
        }.addOnCanceledListener {

            Log.i(TAG, "${event.id} Record failed to be modified")
        }
    }

    fun deleteEvent(event : Event, view: View){
        getDatabaseRef().child(event.id).removeValue().addOnSuccessListener {
            Snackbar.make(view, "Event Record Successfully Deleted", Snackbar.LENGTH_LONG).show()
            Log.i(TAG, "${event.id} Record Deleted")
        }.addOnCanceledListener {
            Log.i(TAG, "${event.id} Record Failed to be deleted")
        }
    }
}
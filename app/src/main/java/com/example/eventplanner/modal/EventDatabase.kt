package com.example.eventplanner.modal

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import java.util.*

object EventDatabase : Observable(){
    const val TAG = "EventDatabase"
    private var imageDB = ImageModal
    private var auth = AuthenticationModal
    var mEventList : MutableLiveData<MutableList<Event>> = MutableLiveData()

    fun getDatabaseRef() : DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("Events")
    }

    init {
        getEventList()
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

    private fun getEventList(){
        getDatabaseRef().orderByChild("userId").equalTo(auth.getUser().value?.uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(events: DataSnapshot) {
                val data: MutableList<Event> = mutableListOf()
                if (events.exists()) {
                    for (e in events.children) {
                        val event = e.getValue(Event::class.java)
                        data.add(event!!)
                    }
                    mEventList.value = data
                    mEventList.postValue(data)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

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
            imageDB.deleteImage(event.id)
            Log.i(TAG, "${event.id} Record Deleted")
        }.addOnCanceledListener {
            Log.i(TAG, "${event.id} Record Failed to be deleted")
        }
    }
}
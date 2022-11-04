package com.example.eventplanner.repository

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eventplanner.viewModel.parcels.Event
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import java.util.*

/**
 * repository used for event
 */
object EventRepository : Observable(){
    const val TAG = "EventDatabase"
    private var imageDB = ImageRepository
    private var auth = AuthenticationRepository
    var mEventList : MutableLiveData<MutableList<Event>> = MutableLiveData()

    fun getEventList(): LiveData<MutableList<Event>> {
        return mEventList
    }

    private fun getDatabaseRef() : DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("Events")
    }

    init {
        retrieveEventList()
    }

    /**
     * creates event record in the database
     */
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

    /**
     * calls data on change to update live data field in real time
     */
    private fun retrieveEventList(){
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

    /**
     * updates event record in the database and displays snack bar once updated
     */
    fun updateEvent(event : Event, view: View){
        getDatabaseRef().child(event.id).setValue(event).addOnSuccessListener {
            Snackbar.make(view, "Event Record Successfully updated", Snackbar.LENGTH_LONG).show()
        }.addOnCanceledListener {

            Log.i(TAG, "${event.id} Record failed to be modified")
        }
    }

    /**
     * re initialises the live data
     */
    fun reInitList(){
        mEventList.value = null
    }

    /**
     * deletes event record from the database and displays snack bar once deleted
     */
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
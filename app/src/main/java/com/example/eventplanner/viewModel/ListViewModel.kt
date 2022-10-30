package com.example.eventplanner.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplanner.modal.AuthenticationModal
import com.example.eventplanner.modal.CategoryDatabase
import com.example.eventplanner.modal.EventDatabase
import com.example.eventplanner.modal.ImageModal
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import java.util.*

class ListViewModel : ViewModel() {

    private val TAG: String = "ListViewModal"
    private var db = EventDatabase
    private var categoryDB = CategoryDatabase
    var eventList : MutableLiveData<MutableList<Event>> = db.mEventList
    var upcomingEvents : MutableLiveData<MutableList<Event>> = MutableLiveData<MutableList<Event>>()
    var missedEvents : MutableLiveData<MutableList<Event>> = MutableLiveData<MutableList<Event>>()
    var completedEvents : MutableLiveData<MutableList<Event>> = MutableLiveData<MutableList<Event>>()


    fun initLists(list: MutableList<Event>){
        viewModelScope.launch {

            upcomingEvents.value = mutableListOf()
            missedEvents.value = mutableListOf()
            completedEvents.value = mutableListOf()

            val currentDate = Date()
            currentDate.year = 1900 + currentDate.year
            for(i in list){
                val event = Event(i.name, i.date,  i.category, i.description,  i.location, i.done, i.imageLoc, i.id, userId = i.userId)
                if(i.done) {
                    completedEvents.value!!.add(event)}
                else{
                    if(i.date > currentDate){
                        upcomingEvents.value!!.add(event)
                    }
                    else {
                        missedEvents.value!!.add(event)
                    }
                }
            }
            Log.i(TAG, completedEvents.value.toString())
            upcomingEvents.postValue(upcomingEvents.value)
            missedEvents.postValue(missedEvents.value)
            completedEvents.postValue(completedEvents.value)
        }

    }


    fun initCategory(){
        viewModelScope.launch {
            listCatInit(upcomingEvents)
            listCatInit(missedEvents)
            listCatInit(completedEvents)
        }
    }

    fun listCatInit(list: MutableLiveData<MutableList<Event>>){
        for (i in list.value ?: mutableListOf()) {
            for (j in categoryDB.mCategoryList.value ?: mutableListOf()) {
                if (i.category == j.id) {
                    i.category = j.name
                }
            }
        }
    }


    fun deleteEvent(event: Event, view: View){
        viewModelScope.launch {
            db.deleteEvent(event, view)
        }
    }

    fun updateEvent(event: Event, view: View){
        viewModelScope.launch {
            db.updateEvent(event, view)
        }
    }

}

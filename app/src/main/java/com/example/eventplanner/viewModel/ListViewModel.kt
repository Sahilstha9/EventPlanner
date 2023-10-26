package com.example.eventplanner.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplanner.repository.CategoryRepository
import com.example.eventplanner.repository.EventRepository
import com.example.eventplanner.viewModel.parcels.Event
import kotlinx.coroutines.launch
import java.util.*

class ListViewModel : ViewModel() {

    private val TAG: String = "ListViewModal"
    private var db = EventRepository
    private var categoryDB = CategoryRepository
    var eventList : LiveData<MutableList<Event>> = db.getEventList()
    var upcomingEvents : MutableLiveData<MutableList<Event>> = MutableLiveData<MutableList<Event>>()
    var missedEvents : MutableLiveData<MutableList<Event>> = MutableLiveData<MutableList<Event>>()
    var completedEvents : MutableLiveData<MutableList<Event>> = MutableLiveData<MutableList<Event>>()

    /**
     * updates value in the upcoming, missed, and completed event list in the live data
     */
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

    /**
     * changes category of event record to display the name of category instead of object id
     */
    private fun listCatInit(list: MutableLiveData<MutableList<Event>>){
        for (i in list.value ?: mutableListOf()) {
            for (j in categoryDB.getCategoryList().value ?: mutableListOf()) {
                if (i.category == j.id) {
                    i.category = j.name
                }
            }
        }
    }

    /**
     * calls delete event from the database to delete the event passed
     */
    fun deleteEvent(event: Event, view: View){
        viewModelScope.launch {
            db.deleteEvent(event, view)
        }
    }

    /**
     * calls update event from the database to update the event passed
     */
    fun updateEvent(event: Event, view: View){
        viewModelScope.launch {
            db.updateEvent(event, view)
        }
    }

}

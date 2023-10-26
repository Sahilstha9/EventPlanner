package com.example.eventplanner.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplanner.repository.EventRepository
import com.example.eventplanner.viewModel.parcels.Event
import kotlinx.coroutines.launch
import java.util.*

class CalendarViewModel : ViewModel() {

    private val TAG: String = "CalendarViewModel"
    private var db = EventRepository
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
                if(i.done) {
                    completedEvents.value!!.add(i)}
                else{
                    if(i.date > currentDate){
                        upcomingEvents.value!!.add(i)
                    }
                    else {
                        missedEvents.value!!.add(i)
                    }
                }
            }
            Log.i(TAG, completedEvents.value.toString())
            upcomingEvents.postValue(upcomingEvents.value)
            missedEvents.postValue(missedEvents.value)
            completedEvents.postValue(completedEvents.value)
        }

    }
}
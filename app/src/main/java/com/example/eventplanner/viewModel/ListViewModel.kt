package com.example.eventplanner.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras.Empty.toString
import com.example.eventplanner.Modal.EventDatabase
import com.example.eventplanner.View.Fragments.BottomSheet.BottomSheetFragment
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
    var eventList : MutableLiveData<MutableList<Event>> = MutableLiveData<MutableList<Event>>()
    var upcomingEvents : MutableLiveData<MutableList<Event>> = MutableLiveData<MutableList<Event>>()
    var missedEvents : MutableLiveData<MutableList<Event>> = MutableLiveData<MutableList<Event>>()
    var completedEvents : MutableLiveData<MutableList<Event>> = MutableLiveData<MutableList<Event>>()

    init {
        listenToEvent()
    }

    private fun listenToEvent() {
        db.getDatabaseRef().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(events: DataSnapshot) {
                val data : MutableList<Event> = mutableListOf()
                if(events.exists()){
                    for (e in events.children){
                        val event = e.getValue(Event::class.java)
                        data.add(event!!)
                    }
                    initLists(data)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun initLists(list: MutableList<Event>){
        viewModelScope.launch {
            eventList.value = list
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
                        Log.i(TAG, "true")
                        upcomingEvents.value!!.add(i)
                    }
                    else {
                        missedEvents.value!!.add(i)
                    }
                }
            }
            upcomingEvents.postValue(upcomingEvents.value)
            missedEvents.postValue(missedEvents.value)
            completedEvents.postValue(completedEvents.value)
        }

    }


    fun hasEvent(date : Date) : Event?{
        for (i in eventList.value!!){
            if(i.date.month == date.month && i.date.day == date.day && i.date.year == date.year){
                return i
            }
        }
        return null
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

package com.example.eventplanner.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventplanner.Modal.EventDatabase
import com.example.eventplanner.View.Fragments.BottomSheet.BottomSheetFragment
import com.example.eventplanner.viewModel.parcels.Event

class ListViewModel : ViewModel() {

    private var db = EventDatabase
    var eventList : MutableLiveData<MutableList<Event>> = MutableLiveData<MutableList<Event>>()
    get() {
        return field
    }

    init {
        eventList.value = db.getEventsOnce()
    }

    fun deleteEvent(event: Event){
        db.deleteEvent(event)
    }

    fun updateEvent(event: Event){
        db.updateEvent(event)
    }

}
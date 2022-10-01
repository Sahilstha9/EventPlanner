package com.example.eventplanner.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventplanner.Modal.EventDatabase
import com.example.eventplanner.viewModel.parcels.Event
import java.util.*

class AddEventViewModel : ViewModel(){

    private var db : EventDatabase = EventDatabase()
    lateinit var name: String
    lateinit var date: Date
    lateinit var category: String
    lateinit var description: String
    lateinit var location: String
    var done: Boolean = false
    lateinit var id: String
    private var _eventList = MutableLiveData<Event>()
    val eventList : LiveData<Event>
    get() = _eventList

    fun inputValidation(){

    }
}
package com.example.eventplanner.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplanner.modal.CategoryDatabase
import com.example.eventplanner.modal.EventDatabase
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import java.util.Date

class InsightsViewModel : ViewModel() {
    private val TAG = "InsightsViewModel"
    val upcomingCount = MutableLiveData<Int>(0)
    val missedCount = MutableLiveData<Int>(0)
    val completedCount = MutableLiveData<Int>(0)
    val categoryList = MutableLiveData<MutableList<String>>()
    private val categoryRef = CategoryDatabase.getDatabaseRef()
    private val eventRef = EventDatabase.getDatabaseRef()

    init {
        listenToCategory()
        listenToEvent()
    }

    private fun listenToCategory() {
        categoryRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(categories: DataSnapshot) {
                val data : MutableList<String> = mutableListOf()
                if(categories.exists()){
                    for (e in categories.children){
                        val category = e.getValue(Category::class.java)
                        data.add(category!!.name)
                    }
                    categoryList.value = data
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun listenToEvent() {
        eventRef.addValueEventListener(object : ValueEventListener {
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

    fun initLists(list: List<Event>){
        viewModelScope.launch {
            val currentDate = Date()
            currentDate.year = 1900 + currentDate.year
            for(i in list){
                if(i.done) {
                    completedCount.value = completedCount.value?.plus(1)
                }
                else{
                    if(i.date > currentDate){
                        upcomingCount.value = upcomingCount.value?.plus(1)
                        Log.i(TAG, upcomingCount.value.toString())
                    }
                    else {
                        missedCount.value = missedCount.value?.plus(1)
                    }
                }
                if(categoryList.value != null){
                    if(categoryList.value?.contains(i.category) == false){
                        categoryList.value!!.add(i.category)
                    }
                }
            }
        }
    }
}
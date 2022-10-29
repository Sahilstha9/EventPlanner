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
    private val categoryDB = CategoryDatabase
    private val eventDB = EventDatabase
    val eventList = eventDB.mEventList
    val upcomingCount = MutableLiveData<Int>(0)
    val missedCount = MutableLiveData<Int>(0)
    val completedCount = MutableLiveData<Int>(0)
    private val categoryList : MutableList<String> = mutableListOf()

    init {
        for (i in categoryDB.mCategoryList.value ?: mutableListOf()){
            categoryList.add(i.name)
        }
    }

    fun initLists(list: List<Event>){
        completedCount.value = 0
        missedCount.value = 0
        upcomingCount.value = 0
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
                if(!categoryList.contains(i.category)){
                    categoryList.add(i.category)
                }
            }
        }
    }
}
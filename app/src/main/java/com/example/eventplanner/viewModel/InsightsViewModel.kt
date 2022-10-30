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
    val catList = categoryDB.mCategoryList
    val eventList = eventDB.mEventList
    val eventWithCategoryNameList = mutableListOf<Event>()
    val upcomingCount = MutableLiveData<Int>(0)
    val missedCount = MutableLiveData<Int>(0)
    val completedCount = MutableLiveData<Int>(0)
    var categoryList : MutableList<String> = mutableListOf()

    fun initCategoryList(){
        categoryList = mutableListOf()
        for (i in catList.value!!){
            for (j in eventWithCategoryNameList){
                if(j.category == i.id){
                    j.category = i.name
                }
            }
            Log.i(TAG, i.name)
            categoryList.add(i.name)
        }
    }

    fun initLists(list: List<Event>){
        completedCount.value = 0
        missedCount.value = 0
        upcomingCount.value = 0
        for(i in eventList.value!!){
            val event = Event(i.name, i.date, i.category, i.description, i.location, i.done, i.imageLoc, i.id, i.userId)
            eventWithCategoryNameList.add(event)
        }
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
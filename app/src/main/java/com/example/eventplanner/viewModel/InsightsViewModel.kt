package com.example.eventplanner.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplanner.repository.CategoryRepository
import com.example.eventplanner.repository.EventRepository
import com.example.eventplanner.viewModel.parcels.Event
import kotlinx.coroutines.launch
import java.util.*

class InsightsViewModel : ViewModel() {
    private val TAG = "InsightsViewModel"
    private val categoryDB = CategoryRepository
    private val eventDB = EventRepository
    val catList = categoryDB.getCategoryList()
    val eventList = eventDB.getEventList()
    val eventWithCategoryNameList = mutableListOf<Event>()
    val upcomingCount = MutableLiveData(0)
    val missedCount = MutableLiveData(0)
    val completedCount = MutableLiveData(0)
    var categoryList : MutableList<String> = mutableListOf()

    /**
     * initialises category list live data
     */
    fun initCategoryList(){
        categoryList = mutableListOf()
        for (i in catList.value ?: mutableListOf()){
            for (j in eventWithCategoryNameList){
                if(j.category == i.id){
                    j.category = i.name
                }
            }
            Log.i(TAG, i.name)
            categoryList.add(i.name)
        }
    }

    /**
     * updates value in the upcoming, missed, and completed event list in the live data
     */
    fun initLists(list: List<Event>){
        completedCount.value = 0
        missedCount.value = 0
        upcomingCount.value = 0
        for(i in eventList.value ?: mutableListOf()){
            val event = Event(i.name, i.date, i.category, i.description, i.location, i.done, i.imageLoc, i.id, i.userId)
            eventWithCategoryNameList.add(event)
        }
        viewModelScope.launch {

            val currentDate = Date()
            @Suppress("DEPRECATION")
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
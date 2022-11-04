package com.example.eventplanner.viewModel.classes

import android.util.Log
import com.example.eventplanner.viewModel.parcels.Event

class PieChartValueGetter {

    /**
     * returns a hash map of category name and number of event of that category
     */
    fun getCategoryList(list : List<String>, eventList : List<Event>) : HashMap<String, Int>{
        val toReturn = HashMap<String, Int>()
        for (i in list){
            Log.i("PIE", i)
            toReturn[i] = getCount(eventList, i)
        }
        Log.i("PIE", toReturn.size.toString())
        return toReturn
    }

    /**
     * counts the total number of event per category
     */
    private fun getCount(list : List<Event>, name : String) : Int{
        var count = 0
        for (i in list){
            if(i.category == name){
                count++
            }
        }
        return count
    }
}
package com.example.eventplanner.viewModel

import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.lifecycle.ViewModel
import com.example.categoryplanner.Modal.CategoryDatabase
import com.example.eventplanner.Modal.EventDatabase
import com.example.eventplanner.viewModel.parcels.Event

class AddFragmentViewModel : ViewModel() {
    private val TAG: String = "AddFragmentViewModel"
    private var dbEvent = EventDatabase
    private var dbCategory = CategoryDatabase

//    fun activityResultLogic(it: ActivityResult, classUsed : Class){
//        val intent = it.data
//        val parcel = intent?.getParcelableExtra<classUsed>("event")!!
//        Log.i(TAG, parcel.name)
//
//        dbEvent.createEvent(parcel!!)
//    }

}
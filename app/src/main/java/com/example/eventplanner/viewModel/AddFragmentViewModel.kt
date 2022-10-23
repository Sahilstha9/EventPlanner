package com.example.eventplanner.viewModel

import androidx.lifecycle.ViewModel
import com.example.eventplanner.modal.CategoryDatabase
import com.example.eventplanner.modal.EventDatabase

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
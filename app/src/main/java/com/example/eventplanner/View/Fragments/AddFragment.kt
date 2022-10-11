package com.example.eventplanner.View.Fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.categoryplanner.Modal.CategoryDatabase
import com.example.eventplanner.Modal.EventDatabase
import com.example.eventplanner.R
import com.example.eventplanner.View.Activities.AddCategoryActivity
import com.example.eventplanner.View.Activities.AddEventActivity
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event

class AddFragment : Fragment(R.layout.fragment_add) {

    private val TAG: String = "AddFragment"
    private lateinit var addEvent: Button
    private lateinit var addCategory: Button
    private var dbEvent = EventDatabase
    private var dbCategory = CategoryDatabase

    val getEvent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK){
            var intent = it.data
            var parcel : Event = intent?.getParcelableExtra("event")!!
            Log.i(TAG, parcel.name)

            dbEvent.createEvent(parcel!!)
        }
    }

    val getCategory = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK) {
            var intent = it.data
            var parcel = intent?.getParcelableExtra<Category>("category")

            dbCategory.createCategory(parcel!!)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val eventActivityIntent = Intent(context, AddEventActivity::class.java)
        val categoryActivityIntent = Intent(context, AddCategoryActivity::class.java)

        addEvent = view.findViewById(R.id.addEvent)
        addCategory = view.findViewById(R.id.addCategory)

        addEvent.setOnClickListener(){
            getEvent.launch(eventActivityIntent)
        }

        addCategory.setOnClickListener(){
            getCategory.launch(categoryActivityIntent)
        }
    }
}
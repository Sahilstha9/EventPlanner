package com.example.eventplanner.view.fragments.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.eventplanner.R
import com.example.eventplanner.viewModel.parcels.Event
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment(item : Event) : BottomSheetDialogFragment(){
    private lateinit var name : TextView
    private lateinit var date : TextView
    private lateinit var category : TextView
    private lateinit var description : TextView
    private lateinit var location : TextView
    private var event : Event = item
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        name = view.findViewById(R.id.name)
        date = view.findViewById(R.id.date)
        category = view.findViewById(R.id.category)
        description = view.findViewById(R.id.description)
        location = view.findViewById(R.id.location)
        dialog?.setOnShowListener { dialog ->
            val d = dialog as BottomSheetDialog
            val bottomSheetInternal =
                d.findViewById<View>(R.id.bottom)
            bottomSheetInternal?.minimumHeight= 300
        }
        name.text = "Name : ${event.name}"
        date.text = "${event.date.date}/ ${event.date.month}/${event.date.year}"
        location.text = "Location : ${event.location}"
        category.text = "Category : ${event.category}"
        description.text = event.description
    }
}
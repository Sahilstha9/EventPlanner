package com.example.eventplanner.View.Fragments

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.View.Adapters.CalendarAdapter
import com.example.eventplanner.viewModel.ListViewModel
import com.example.eventplanner.viewModel.parcels.Event

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private lateinit var calendar : CalendarView
    private val TAG: String = "CalendarFragment"
    private lateinit var viewModel : ListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendar = view.findViewById(R.id.calendarView)


        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        val eventList = view.findViewById<RecyclerView>(R.id.recyclerView)

        viewModel.eventList.observe(viewLifecycleOwner, Observer {
            val data = it
            val adapter = CalendarAdapter(data)
            eventList.layoutManager = LinearLayoutManager(context)
            eventList.adapter = adapter
        })
//        val data : MutableList<Event> =
//        val adapter = CalendarAdapter(data)
//        eventList.layoutManager = LinearLayoutManager(context)
//        eventList.adapter = adapter
    }
}
package com.example.eventplanner.View.Fragments

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.View.Adapters.CalendarAdapter
import com.example.eventplanner.viewModel.ListViewModel
import com.example.eventplanner.viewModel.classes.CurrentDayDecorator
import com.example.eventplanner.viewModel.parcels.Event
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener
import java.util.*


class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private lateinit var calendar: com.prolificinteractive.materialcalendarview.MaterialCalendarView
    private val TAG: String = "CalendarFragment"
    private lateinit var viewModel: ListViewModel
    private lateinit var textView: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.textView)

        textView.text = "Events"
        calendar = view.findViewById(R.id.calendarView)
        recyclerView = view.findViewById(R.id.recyclerView)
        val myDate = CalendarDay.today()
        calendar.addDecorator(
            CurrentDayDecorator(
                activity,
                myDate,
                ContextCompat.getDrawable(requireContext(), R.color.teal_700)
            )
        )
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]

        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = CalendarAdapter(mutableListOf(), requireContext())
        recyclerView.adapter = adapter

        recyclerViewValueAdd(CalendarDay.today(), adapter)
        calendar.setOnMonthChangedListener(OnMonthChangedListener{view, date ->
            recyclerViewValueAdd(date, adapter)
            calendar.invalidate()
        })

        calendarDecorator(viewModel.upcomingEvents, ContextCompat.getDrawable(requireContext(), R.color.upcoming))
        calendarDecorator(viewModel.missedEvents, ContextCompat.getDrawable(requireContext(), R.color.missed))
        calendarDecorator(viewModel.completedEvents, ContextCompat.getDrawable(requireContext(), R.color.done))

    }

    private fun recyclerViewValueAdd(date : CalendarDay, adapter: CalendarAdapter){
        viewModel.eventList.observe(viewLifecycleOwner, Observer {
            val c : MutableList<Event> = mutableListOf()
            for (i in it){
                if(i.date.year == date.year){
                    if (i.date.month + 1 == date.month){
                        val event = Event(i.name, Date(i.date.year, i.date.month, i.date.date, i.date.hours, i.date.minutes),  i.category, i.description,  i.location, i.done)
                        c.add(event)
                    }
                }
            }
            adapter.setData(c)
            adapter.notifyDataSetChanged()
        })
    }

    private fun calendarDecorator(list : MutableLiveData<MutableList<Event>>, drawable: Drawable?){
        list.observe(viewLifecycleOwner, Observer {
            for (i in it) {
                val calendarDay = CalendarDay.from(i.date.year, i.date.month + 1, i.date.date)
                calendar.addDecorator(
                    CurrentDayDecorator(
                        activity,
                        calendarDay,
                        drawable
                    )
                )
            }
        })
    }
}
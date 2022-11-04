package com.example.eventplanner.view.fragments

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.view.adapters.CalendarAdapter
import com.example.eventplanner.view.fragments.bottomSheet.BottomSheetFragment
import com.example.eventplanner.viewModel.CalendarViewModel
import com.example.eventplanner.viewModel.classes.CurrentDayDecorator
import com.example.eventplanner.viewModel.parcels.Event
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener
import java.util.*


class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    private lateinit var calendar: com.prolificinteractive.materialcalendarview.MaterialCalendarView
    private val TAG: String = "CalendarFragment"
    private lateinit var viewModel: CalendarViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendar = view.findViewById(R.id.calendarView)
        recyclerView = view.findViewById(R.id.recyclerView)

        calendar.addDecorator(
            CurrentDayDecorator(
                activity,
                CalendarDay.today(),
                ContextCompat.getDrawable(requireContext(), R.color.teal_700)
            )
        )

        viewModel = ViewModelProvider(this)[CalendarViewModel::class.java]

        viewModel.eventList.observe(viewLifecycleOwner, Observer {
            viewModel.initLists(it)
        })

        recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = CalendarAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter

        recyclerViewValueAdd(CalendarDay.today(), adapter)
        calendar.setOnMonthChangedListener(OnMonthChangedListener{view, date ->
            recyclerViewValueAdd(date, adapter)
            calendar.invalidate()
        })

    }

    override fun onResume() {
        super.onResume()
        calendarDecorator(viewModel.upcomingEvents, ContextCompat.getDrawable(requireContext(), R.color.upcoming))
        calendarDecorator(viewModel.missedEvents, ContextCompat.getDrawable(requireContext(), R.color.missed))
        calendarDecorator(viewModel.completedEvents, ContextCompat.getDrawable(requireContext(), R.color.done))
    }

    /**
     * initialises observer for the event list live data
     */
    private fun recyclerViewValueAdd(date : CalendarDay, adapter: CalendarAdapter){
        viewModel.eventList.observe(viewLifecycleOwner, Observer {
            val c : MutableList<Event> = mutableListOf()
            for (i in it){
                if(i.date.year == date.year){
                    if (i.date.month + 1 == date.month){
                        val event = Event(i.name, Date(i.date.year, i.date.month, i.date.date, i.date.hours, i.date.minutes),  i.category, i.description,  i.location, i.done, userId = i.userId)
                        c.add(event)
                    }
                }
            }
            adapter.setData(c)
            adapter.notifyDataSetChanged()
        })
    }

    /**
     * displays bottom sheet fragment and also fills the details in it with the values passed
     */
    fun showDialog(item: Event){
        val bottomSheet = BottomSheetFragment(item)
        bottomSheet.show(childFragmentManager, "Long")
    }

    /**
     * colour codes dates in the calendar view according to their status of completion
     */
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
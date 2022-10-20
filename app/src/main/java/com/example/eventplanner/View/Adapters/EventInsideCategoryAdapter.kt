package com.example.eventplanner.View.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.View.Activities.MainActivity
import com.example.eventplanner.View.Fragments.CalendarFragment
import com.example.eventplanner.viewModel.parcels.Event
import com.google.android.material.chip.Chip
import java.util.*

class EventInsideCategoryAdapter(private var data: List<Event>) : RecyclerView.Adapter<EventInsideCategoryAdapter.ViewHolder>()  {

    private val TAG: String = "CalendarAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventInsideCategoryAdapter.ViewHolder {
        Log.i(TAG, "Created")
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.simple_row, parent, false) as View
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: EventInsideCategoryAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    public fun setData(_data : List<Event>){
        data = _data
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        private val name: TextView = v.findViewById(R.id.name)

        fun bind(item: Event) {
            name.text = item.name
        }

    }

}
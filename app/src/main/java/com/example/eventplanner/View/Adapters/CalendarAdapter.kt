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

class CalendarAdapter(private var data: List<Event>, context: CalendarFragment) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>()  {

    private val TAG: String = "CalendarAdapter"
    private val context : CalendarFragment = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAdapter.ViewHolder {
        Log.i(TAG, "Created")
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.layout_list_simple_row, parent, false) as View
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CalendarAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    public fun setData(_data : List<Event>){
        data = _data
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        private val name: TextView = v.findViewById(R.id.name)
        private val date: TextView = v.findViewById(R.id.date)
        private val chip: Chip = v.findViewById(R.id.chip)

        fun bind(item: Event) {
            if (item.done) {chip.setBackgroundColor(context.resources.getColor(R.color.done))}
            else{
                if(item.date > Date()){
                    chip.setBackgroundColor(context.resources.getColor(R.color.upcoming))
                }
                else{
                    chip.setBackgroundColor(context.resources.getColor(R.color.missed))
                }
            }
            name.text = item.name
            var d = item.date.date.toString()
            date.text = d
            v.setOnClickListener { }
        }

    }

}
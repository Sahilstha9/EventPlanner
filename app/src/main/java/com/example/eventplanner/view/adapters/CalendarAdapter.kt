package com.example.eventplanner.view.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.view.fragments.ListFragment
import com.example.eventplanner.viewModel.parcels.Event
import com.google.android.material.chip.Chip
import java.util.*


class CalendarAdapter(private var data: List<Event>, context: Context) : RecyclerView.Adapter<CalendarAdapter.ViewHolder>()  {

    private val TAG: String = "CalendarAdapter"
    private val context : Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAdapter.ViewHolder {
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
            val currentDate = Date()
            currentDate.year = currentDate.year + 1900
            if (item.done) {
                chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.done))
            }
            else{
                if(item.date > currentDate){
                    chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.upcoming))
                }
                else{
                    chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.missed))
                }
            }
            chip.invalidate()
            name.text = item.name
            date.text = item.date.date.toString()

            v.setOnClickListener(){
                ListFragment().showDialog(item)
            }
        }

    }

}
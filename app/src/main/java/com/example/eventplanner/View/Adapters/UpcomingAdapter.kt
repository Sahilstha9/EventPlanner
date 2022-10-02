package com.example.eventplanner.View.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R

class UpcomingAdapter(private val data: List<Int>) : RecyclerView.Adapter<UpcomingAdapter.ViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.layout_upcoming_row, parent, false) as View
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: UpcomingAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        private val name: TextView = v.findViewById(R.id.name)

        fun bind(item: Int) {
            name.text = item.toString()
            v.setOnClickListener { }
        }

    }

}
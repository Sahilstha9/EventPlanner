package com.example.eventplanner.view.adapters


import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.view.ViewGroup
import android.widget.*

import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.repository.ImageRepository
import com.example.eventplanner.R
import com.example.eventplanner.view.fragments.ListFragment

import com.example.eventplanner.viewModel.parcels.Event

class ListRowViewAdapter(private val data: List<Event>, var fragment: ListFragment) : RecyclerView.Adapter<ListRowViewAdapter.ViewHolder>() {

    private val TAG: String = "UpcomingAdapter"
    private val imageDB = ImageRepository

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListRowViewAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.layout_list_row, parent, false) as View
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ListRowViewAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v), OnCreateContextMenuListener  {
        private val name: TextView = v.findViewById(R.id.name)
        private val date: TextView = v.findViewById(R.id.date)
        private var image : ImageView = v.findViewById(R.id.imageView)
        private var isDone : Boolean = false

        init{
            v.setOnCreateContextMenuListener(this)
        }

        fun bind(item: Event) {
            name.text = item.name
            var d = item.date.toString()
            date.text = "${d.slice(0..15)} ${item.date.year}"
            isDone= item.done
            imageDB.getImage(image, item.id)
            v.setOnClickListener {
                fragment.showDialog(item)
            }
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            menu!!.setHeaderTitle("Menu Options")
            menu.add(adapterPosition, 101, 0, "Edit")
            menu.add(adapterPosition, 102, 0, "Delete")
            menu.add(adapterPosition, 103, 0, if(isDone){ "Mark as not Done"} else {"Mark as Done"})
        }

    }
    fun getEvent(position: Int): Event {
        return data[position]
    }

}
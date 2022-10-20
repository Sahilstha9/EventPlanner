package com.example.eventplanner.View.Adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.View.Fragments.ListFragment

import com.example.eventplanner.viewModel.parcels.Event

class UpcomingAdapter(private val data: List<Event>, var fragment: ListFragment) : RecyclerView.Adapter<UpcomingAdapter.ViewHolder>()  {

    private val TAG: String = "UpcomingAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.layout_list_row, parent, false) as View
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: UpcomingAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        private val name: TextView = v.findViewById(R.id.name)
        private val date: TextView = v.findViewById(R.id.date)
        private var menu : ImageView = v.findViewById(R.id.menu)

        private fun popContextMenu(event: Event) {
            val popupMenu = PopupMenu(fragment.context, menu)
            popupMenu.inflate(R.menu.context_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId){
                    R.id.edit->{
                        fragment.editEvent(event)
                        true
                    }
                    R.id.delete->{
                        fragment.deleteEvent(event)
                        true
                    }
                    else -> true
                }
            }
            popupMenu.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenu)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }

        fun bind(item: Event) {
            name.text = item.name
            date.text = item.date.toString()
            v.setOnClickListener {
                fragment.showDialog(item)
            }
            menu.setOnClickListener(){
                popContextMenu(item)
            }
        }

    }

}
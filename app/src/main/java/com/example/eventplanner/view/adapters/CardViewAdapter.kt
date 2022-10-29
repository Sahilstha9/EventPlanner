package com.example.eventplanner.view.adapters

import android.content.res.ColorStateList
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.modal.ImageModal
import com.example.eventplanner.R
import com.example.eventplanner.view.fragments.ListFragment
import com.example.eventplanner.viewModel.parcels.Event

class CardViewAdapter(private var data: List<Event>, var fragment: ListFragment) : RecyclerView.Adapter<CardViewAdapter.ViewHolder>()  {

    private val imageDB = ImageModal

    fun setData(v : List<Event>){
        data = v
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.layout_cardview_linear_row, parent, false) as View
        return ViewHolder(view)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CardViewAdapter.ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v), OnCreateContextMenuListener {
        private val name: TextView = v.findViewById(R.id.name)
        private val date: TextView = v.findViewById(R.id.date)
        private val image: ImageView = v.findViewById(R.id.image)
        private val description: TextView = v.findViewById(R.id.description)
        private var isDone : Boolean = false

        init{
            v.setOnCreateContextMenuListener(this)
        }


        fun bind(item: Event) {
            name.text = item.name
            var d = item.date.toString()
            date.text = "${d.slice(0..15)} ${item.date.year}"
            description.text = item.description
            imageDB.getImage(image, item.id)
            isDone= item.done
            description.text = item.description
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
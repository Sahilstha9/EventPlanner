package com.example.eventplanner.view.adapters

import android.view.ContextMenu
import android.view.LayoutInflater
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

class GridViewAdapter(var fragment : ListFragment) : RecyclerView.Adapter<GridViewAdapter.ViewHolder>()  {

    var dataList = emptyList<Event>()
    private val imageDB = ImageModal

    internal fun setDataList(dataList : List<Event>){
        this.dataList = dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridViewAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.layout_grid_recyclerview, parent, false) as View
        return ViewHolder(view)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: GridViewAdapter.ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v), OnCreateContextMenuListener {
        private val name: TextView = v.findViewById(R.id.name)
        private val date: TextView = v.findViewById(R.id.date)
        private val image: ImageView = v.findViewById(R.id.image)
        private var isDone : Boolean = false

        init{
            v.setOnCreateContextMenuListener(this)
        }


        fun bind(item: Event) {
            name.text = item.name
            var d = item.date.toString()
            date.text = "${d.slice(0..15)} ${item.date.year}"
            imageDB.getImage(image, item.id)
            isDone= item.done
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
        return dataList[position]
    }

}
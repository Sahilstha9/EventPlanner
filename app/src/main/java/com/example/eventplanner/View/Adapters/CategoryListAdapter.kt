package com.example.eventplanner.View.Adapters

import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.View.Fragments.CategoryListFragment
import com.example.eventplanner.viewModel.parcels.CategoriesWithEvent
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event
import org.w3c.dom.Text
import java.util.*

class CategoryListAdapter(private val fragment : CategoryListFragment)  : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>(){
    private val TAG: String = "CategoryListAdapter"
    var dataList = emptyList<CategoriesWithEvent>()

    internal fun setDataList(dataList : List<CategoriesWithEvent>){
        this.dataList = dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryListAdapter.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.layout_list_row_category, parent, false) as View
        return ViewHolder(view)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: CategoryListAdapter.ViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v), OnCreateContextMenuListener {
        private val name: TextView = v.findViewById(R.id.name)
        private var recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView)
        private var menu : ImageView = v.findViewById(R.id.menu)


        init {
            v.setOnCreateContextMenuListener(this)
        }
        fun bind(item: CategoriesWithEvent) {
            name.text = item.category!!.name
            recyclerView.layoutManager = LinearLayoutManager(fragment.context)
            v.setBackgroundColor(fragment.resources.getColor(androidx.appcompat.R.color.material_grey_100))
            val c = mutableListOf<Event>()
            for (i in item.events!!){
                c.add(i)
            }
            val adapter = EventInsideCategoryAdapter(c, fragment)
            recyclerView.adapter = adapter
            menu.setOnClickListener {
                val params : ViewGroup.LayoutParams = recyclerView.layoutParams
                if(params.height == ViewGroup.LayoutParams.WRAP_CONTENT){
                    params.height = 0
                }
                else{
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                }
                recyclerView.layoutParams = params
            }

            v.setOnClickListener() {
                fragment.showDialog(item.category!!)
                true
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
        }

    }

    fun getCategory(position: Int): Category {
        return dataList[position].category!!
    }
}

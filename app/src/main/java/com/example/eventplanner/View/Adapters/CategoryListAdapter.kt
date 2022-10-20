package com.example.eventplanner.View.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.View.Fragments.CategoryListFragment
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event
import org.w3c.dom.Text
import java.util.*

class CategoryListAdapter(private val fragment : CategoryListFragment)  : RecyclerView.Adapter<CategoryListAdapter.ViewHolder>(){
    var dataList = emptyList<Category>()

    internal fun setDataList(dataList : List<Category>){
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

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v) {
        private val name: TextView = v.findViewById(R.id.name)
        private var recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView)
        private var menu : ImageView = v.findViewById(R.id.menu)

        private fun popContextMenu(category: Category) {
            val popupMenu = PopupMenu(fragment.context, menu)
            popupMenu.inflate(R.menu.context_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId){
                    R.id.edit->{
                        fragment.editCategory(category)
                        true
                    }
                    R.id.delete->{
                        fragment.deleteCategory(category)
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

        fun bind(item: Category) {
            name.text = item.name
            recyclerView.layoutManager = LinearLayoutManager(fragment.context)
            val adapter = EventInsideCategoryAdapter(populateArray())
            recyclerView.adapter = adapter
            v.setOnClickListener {
                var params : ViewGroup.LayoutParams = recyclerView.layoutParams
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                recyclerView.layoutParams = params
//                fragment.showDialog(item)
            }
            menu.setOnClickListener(){
                popContextMenu(item)
            }
        }

        fun populateArray(): MutableList<Event> {
            return mutableListOf<Event>(
                Event("a", Date(1, 2, 2), "c", "d", "e", true),
                Event("a", Date(1, 2, 2), "c", "d", "e", true),
                Event("a", Date(1, 2, 2), "c", "d", "e", true),
                Event("a", Date(1, 2, 2), "c", "d", "e", true)
            )
        }

    }
}

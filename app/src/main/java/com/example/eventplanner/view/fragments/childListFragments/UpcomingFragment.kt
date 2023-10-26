package com.example.eventplanner.view.fragments.childListFragments

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.view.adapters.ListRowViewAdapter
import com.example.eventplanner.view.fragments.ListFragment
import com.example.eventplanner.viewModel.ListViewModel

class UpcomingFragment : Fragment(R.layout.fragment_upcoming) {
    private val TAG: String = "UpcomingFragment"
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel : ListViewModel
    private lateinit var adapter: ListRowViewAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        viewModel.eventList.observe(viewLifecycleOwner, Observer {
            viewModel.initLists(it ?: mutableListOf())
            viewModel.initCategory()
        })

        viewModel.upcomingEvents.observe(viewLifecycleOwner, Observer {
            adapter = ListRowViewAdapter(it, parentFragment as ListFragment)
            recyclerView.adapter = adapter
        })
    }

    fun setViewModel(v : ListViewModel){
        viewModel = v
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            101 -> {
                (parentFragment as ListFragment).editEvent(adapter.getEvent(item.groupId))
                true
            }
            102 -> {
                (parentFragment as ListFragment).deleteEvent(adapter.getEvent(item.groupId))
                true
            }
            103 ->{
                (parentFragment as ListFragment).alternateDone(adapter.getEvent(item.groupId))
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}
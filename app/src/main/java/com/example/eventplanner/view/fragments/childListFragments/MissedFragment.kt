package com.example.eventplanner.view.fragments.childListFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.view.adapters.CardViewAdapter
import com.example.eventplanner.view.fragments.ListFragment
import com.example.eventplanner.viewModel.ListViewModel

class MissedFragment : Fragment(R.layout.fragment_missed) {

    private val TAG = "MissedFragment"
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CardViewAdapter
    private lateinit var viewModel: ListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CardViewAdapter(listOf(), parentFragment as ListFragment)
        recyclerView.adapter = adapter
        viewModel.missedEvents.observe(requireActivity(), androidx.lifecycle.Observer {
            adapter.setData(it)
            adapter.notifyDataSetChanged()
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
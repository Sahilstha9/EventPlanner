package com.example.eventplanner.View.Fragments.ChildListFragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.eventplanner.R
import com.example.eventplanner.View.Activities.AddEventActivity
import com.example.eventplanner.View.Adapters.MissedAdapter
import com.example.eventplanner.View.Adapters.UpcomingAdapter
import com.example.eventplanner.View.Fragments.BottomSheet.BottomSheetFragment
import com.example.eventplanner.View.Fragments.ListFragment
import com.example.eventplanner.viewModel.ListViewModel
import com.example.eventplanner.viewModel.parcels.Event

class UpcomingFragment : Fragment(R.layout.fragment_upcoming) {
    private val TAG: String = "UpcomingFragment"
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel : ListViewModel
    private lateinit var adapter: UpcomingAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.upcomingEvents.observe(viewLifecycleOwner, Observer {
            adapter = UpcomingAdapter(it, parentFragment as ListFragment)
            recyclerView.adapter = adapter
        })
    }

    fun setViewModel(v : ListViewModel){
        viewModel = v
    }
}
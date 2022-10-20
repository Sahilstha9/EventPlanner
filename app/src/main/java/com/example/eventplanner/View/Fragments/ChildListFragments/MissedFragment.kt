package com.example.eventplanner.View.Fragments.ChildListFragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.View.Activities.AddEventActivity
import com.example.eventplanner.View.Adapters.DoneAdapter
import com.example.eventplanner.View.Adapters.MissedAdapter
import com.example.eventplanner.View.Adapters.UpcomingAdapter
import com.example.eventplanner.View.Fragments.BottomSheet.BottomSheetFragment
import com.example.eventplanner.View.Fragments.ListFragment
import com.example.eventplanner.viewModel.ListViewModel
import com.example.eventplanner.viewModel.parcels.Event
import java.util.*

class MissedFragment : Fragment(R.layout.fragment_missed) {

    private val TAG = "MissedFragment"
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MissedAdapter
    private lateinit var viewModel: ListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.missedEvents.observe(requireActivity(), androidx.lifecycle.Observer {
            adapter = MissedAdapter(it, parentFragment as ListFragment)
            recyclerView.adapter = adapter
        })
    }

    fun setViewModel(v : ListViewModel){
        viewModel = v
    }
}
package com.example.eventplanner.View.Fragments.ChildListFragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.eventplanner.viewModel.ListViewModel
import com.example.eventplanner.viewModel.parcels.Event

class UpcomingFragment : Fragment(R.layout.fragment_upcoming) {
    private val TAG: String = "UpcomingFragment"
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel : ListViewModel
    private lateinit var adapter: UpcomingAdapter
    private val getEvent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK){
            val intent = it.data
            val parcel : Event = intent?.getParcelableExtra("event")!!
            Log.i(TAG, parcel.name)

            viewModel.updateEvent(parcel)
        }
    }
    var eventActivityIntent : Intent? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.eventList.observe(viewLifecycleOwner, Observer {
            adapter = UpcomingAdapter(it, this)
            recyclerView.adapter = adapter
        })
    }

    fun showDialog(item : Event) {
        val bottomSheet = BottomSheetFragment(item)
        bottomSheet.show(childFragmentManager, "Long")
    }

    fun deleteEvent(event: Event) {viewModel.deleteEvent(event)}

    fun editEvent(event: Event) {
        eventActivityIntent = Intent(context, AddEventActivity::class.java)
        eventActivityIntent!!.putExtra("event", event)
        getEvent.launch(eventActivityIntent)
    }
}
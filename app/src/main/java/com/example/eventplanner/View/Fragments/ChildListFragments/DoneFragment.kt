package com.example.eventplanner.View.Fragments.ChildListFragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.View.Activities.AddEventActivity
import com.example.eventplanner.View.Activities.MainActivity
import com.example.eventplanner.View.Adapters.DoneAdapter
import com.example.eventplanner.View.Fragments.BottomSheet.BottomSheetFragment
import com.example.eventplanner.viewModel.ListViewModel
import com.example.eventplanner.viewModel.parcels.Event
import java.util.*

class DoneFragment : Fragment(R.layout.fragment_done) {

    private val TAG = "DoneFragment"
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DoneAdapter
    private lateinit var viewModel : ListViewModel
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
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireActivity().applicationContext, 2)
        adapter = DoneAdapter(this)

        viewModel.eventList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.setDataList(it)
        })
        recyclerView.adapter = adapter
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
package com.example.eventplanner.View.Fragments

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.eventplanner.R
import com.example.eventplanner.View.Activities.AddEventActivity
import com.example.eventplanner.View.Fragments.BottomSheet.BottomSheetFragment
import com.example.eventplanner.View.Fragments.ChildListFragments.DoneFragment
import com.example.eventplanner.View.Fragments.ChildListFragments.MissedFragment
import com.example.eventplanner.View.Fragments.ChildListFragments.UpcomingFragment
import com.example.eventplanner.viewModel.ListViewModel
import com.example.eventplanner.viewModel.parcels.Event

class ListFragment : Fragment(R.layout.fragment_list) {

    internal lateinit var viewModal : ListViewModel
    private val getEvent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK){
            val intent = it.data
            val parcel : Event = intent?.getParcelableExtra("event")!!
            Log.i(TAG, parcel.name)

            viewModal.updateEvent(parcel, this.requireView())
        }
    }
    var eventActivityIntent : Intent? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fm = childFragmentManager

        val missed = view.findViewById<Button>(R.id.missed)
        val upcoming = view.findViewById<Button>(R.id.upcoming)
        val done = view.findViewById<Button>(R.id.done)

        viewModal = ViewModelProvider(this)[ListViewModel::class.java]
        val missedFragment = MissedFragment()
        val upcomingFragment = UpcomingFragment()
        val doneFragment = DoneFragment()

        missedFragment.setViewModel(viewModal)
        upcomingFragment.setViewModel(viewModal)
        doneFragment.setViewModel(viewModal)
        missed.setOnClickListener(){
            replaceFragment(fm, missedFragment)
        }

        upcoming.setOnClickListener(){
            replaceFragment(fm, upcomingFragment)
        }

        done.setOnClickListener(){
            replaceFragment(fm, doneFragment)
        }
    }

    fun replaceFragment(fm : FragmentManager, fragment : Fragment){
        Log.i(TAG, "Replace Fragment called")

        fm.beginTransaction().apply {
            replace(R.id.fragmentHolder, fragment)
            commit()
        }
    }

    fun showDialog(item : Event) {
        val bottomSheet = BottomSheetFragment(item)
        bottomSheet.show(childFragmentManager, "Long")
    }

    fun deleteEvent(event: Event) {viewModal.deleteEvent(event, this.requireView())}

    fun editEvent(event: Event) {
        eventActivityIntent = Intent(context, AddEventActivity::class.java)
        eventActivityIntent!!.putExtra("event", event)
        getEvent.launch(eventActivityIntent)
    }

}
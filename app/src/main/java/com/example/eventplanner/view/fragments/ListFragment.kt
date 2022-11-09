package com.example.eventplanner.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.eventplanner.R
import com.example.eventplanner.view.activities.AddEventActivity
import com.example.eventplanner.view.fragments.bottomSheet.BottomSheetFragment
import com.example.eventplanner.view.fragments.childListFragments.DoneFragment
import com.example.eventplanner.view.fragments.childListFragments.MissedFragment
import com.example.eventplanner.view.fragments.childListFragments.UpcomingFragment
import com.example.eventplanner.viewModel.ListViewModel
import com.example.eventplanner.viewModel.parcels.Event
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout

class ListFragment : Fragment(R.layout.fragment_list) {

    private val TAG = "ListFragment"
    internal lateinit var viewModel : ListViewModel
    private lateinit var getEvent : ActivityResultLauncher<Intent>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fm = childFragmentManager

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        val missedFragment = MissedFragment()
        val upcomingFragment = UpcomingFragment()
        val doneFragment = DoneFragment()

        viewModel.eventList.observe(viewLifecycleOwner, Observer {
            viewModel.initLists(it ?: mutableListOf())
            viewModel.initCategory()
        })

        missedFragment.setViewModel(viewModel)
        upcomingFragment.setViewModel(viewModel)
        doneFragment.setViewModel(viewModel)

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position){
                    2 ->{
                        replaceFragment(fm, doneFragment)
                        tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.done))
                    }
                    1 -> {
                        replaceFragment(fm, missedFragment)
                        tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.missed))
                    }
                    0 -> {
                        replaceFragment(fm, upcomingFragment)
                        tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.upcoming))
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })

        getEvent = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val intent = it.data
                val parcel: Event = intent?.getParcelableExtra("event")!!

                viewModel.updateEvent(parcel, this.requireView())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "DESTROYED")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "STOPPED")
    }

    fun replaceFragment(fm : FragmentManager, fragment : Fragment){
        Log.i(TAG, "Replace Fragment called")

        fm.beginTransaction().apply {
            replace(R.id.fragmentHolder, fragment)
            commit()
        }
    }

    /**
     * displays bottom sheet fragment and also fills the details in it with the values passed
     */
    fun showDialog(item : Event) {
        val bottomSheet = BottomSheetFragment(item)
        bottomSheet.show(childFragmentManager, "Long")
    }

    /**
     * displays a dialog box warning the user and calls delete function if the user is sure to delete it
     */
    fun deleteEvent(event: Event) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Warning")
            .setMessage("You will lose the data permanently\nAre you sure?")
            .setNegativeButton("No"){ dialog, which->

            }
            .setPositiveButton("Yes"){dialog, which->
                viewModel.deleteEvent(event, this.requireView())
            }.show()
    }

    /**
     * marks the event as done or not done
     */
    fun alternateDone(event: Event){
        event.done = !event.done
        viewModel.updateEvent(event, requireView())
    }

    /**
     * lauches a new activity for result
     */
    fun editEvent(event: Event) {
        val eventActivityIntent = Intent(context, AddEventActivity::class.java)
        eventActivityIntent.putExtra("event", event)
        getEvent.launch(eventActivityIntent)
    }

}
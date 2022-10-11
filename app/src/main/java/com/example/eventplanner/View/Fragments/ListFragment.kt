package com.example.eventplanner.View.Fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import com.example.eventplanner.R
import com.example.eventplanner.View.Fragments.ChildListFragments.DoneFragment
import com.example.eventplanner.View.Fragments.ChildListFragments.MissedFragment
import com.example.eventplanner.View.Fragments.ChildListFragments.UpcomingFragment

class ListFragment : Fragment(R.layout.fragment_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var fm = childFragmentManager

        var missed = view.findViewById<Button>(R.id.missed)
        var upcoming = view.findViewById<Button>(R.id.upcoming)
        var done = view.findViewById<Button>(R.id.done)

        missed.setOnClickListener(){
            replaceFragment(fm, MissedFragment())
        }

        upcoming.setOnClickListener(){
            replaceFragment(fm, UpcomingFragment())
        }

        done.setOnClickListener(){
            replaceFragment(fm, DoneFragment())
        }
    }

    fun replaceFragment(fm : FragmentManager, fragment : Fragment){
        Log.i(TAG, "Replace Fragment called")

        fm.beginTransaction().apply {
            replace(R.id.fragmentHolder, fragment)
            commit()
        }
    }

}
package com.example.eventplanner.View.Fragments.ChildListFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.View.Adapters.UpcomingAdapter

class UpcomingFragment : Fragment(R.layout.fragment_upcoming) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val upcomingList = view.findViewById<RecyclerView>(R.id.recyclerView)

        val data = IntArray(100) { it }.filter { it % 3 == 0 || it % 5 == 0}
        val adapter = UpcomingAdapter(data)
        upcomingList.layoutManager = LinearLayoutManager(context)
        upcomingList.adapter = adapter
    }
}
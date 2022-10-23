package com.example.eventplanner.View.Fragments.ChildListFragments

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.View.Adapters.GridViewAdapter
import com.example.eventplanner.View.Fragments.ListFragment
import com.example.eventplanner.viewModel.ListViewModel

class DoneFragment : Fragment(R.layout.fragment_done) {

    private val TAG = "DoneFragment"
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GridViewAdapter
    private lateinit var viewModel : ListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ListViewModel::class.java]
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireActivity().applicationContext, 2)
        adapter = GridViewAdapter(parentFragment as ListFragment)

        viewModel.completedEvents.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.setDataList(it)
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
package com.example.eventplanner.view.fragments

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.view.activities.AddCategoryActivity
import com.example.eventplanner.view.adapters.CategoryListAdapter
import com.example.eventplanner.view.fragments.bottomSheet.CategoryBottomSheetFragment
import com.example.eventplanner.viewModel.CategoryViewModel
import com.example.eventplanner.viewModel.parcels.Category
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CategoryListFragment : Fragment(R.layout.fragment_category_list) {
    private val TAG = "CategoryListFragment"
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryListAdapter
    private lateinit var viewModel : CategoryViewModel

    private val getCategory = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == Activity.RESULT_OK){
            val intent = it.data
            val parcel : Category = intent?.getParcelableExtra("category")!!
            Log.i(ContentValues.TAG, parcel.name)

            viewModel.updateCategory(parcel, this.requireView())
        }
    }
    var categoryActivityIntent : Intent? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[CategoryViewModel::class.java]
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = CategoryListAdapter(this)
        recyclerView.adapter = adapter

//        viewModel.cList.observe(viewLifecycleOwner, Observer {
//        })

        viewModel.evenList.observe(viewLifecycleOwner, Observer {
            viewModel.getEvents()
        })
        viewModel.categoryList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.categoryList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
        })

    }

    /**
     * displays bottom sheet fragment and also fills the details in it with the values passed
     */
    fun showDialog(item : Category) {
        val bottomSheet = CategoryBottomSheetFragment(item)
        bottomSheet.show(childFragmentManager, "Long")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            101 -> {
               editCategory(adapter.getCategory(item.groupId))
                true
            }
            102 -> {
                deleteCategory(adapter.getCategory(item.groupId))
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    /**
     * launches a new activity for result
     */
    private fun editCategory(category: Category){
        categoryActivityIntent = Intent(context, AddCategoryActivity::class.java)
        categoryActivityIntent!!.putExtra("category", category)
        getCategory.launch(categoryActivityIntent)
    }

    /**
     * displays a dialog box warning the user and calls delete function if the user is sure to delete it
     */
    private fun deleteCategory(category: Category){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Warning")
            .setMessage("You will lose the data permanently\nAre you sure?")
            .setNegativeButton("No"){ dialog, which->

            }
            .setPositiveButton("Yes"){dialog, which->
                viewModel.deleteCategory(category, requireView())
            }.show()
    }
}
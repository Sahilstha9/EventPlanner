package com.example.eventplanner.View.Fragments

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.View.Activities.AddCategoryActivity
import com.example.eventplanner.View.Adapters.CategoryListAdapter
import com.example.eventplanner.View.Fragments.BottomSheet.CategoryBottomSheetFragment
import com.example.eventplanner.viewModel.CategoryViewModel
import com.example.eventplanner.viewModel.parcels.CategoriesWithEvent
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

        viewModel.categoryList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
        })
    }

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


    private fun editCategory(category: Category){
        categoryActivityIntent = Intent(context, AddCategoryActivity::class.java)
        categoryActivityIntent!!.putExtra("category", category)
        getCategory.launch(categoryActivityIntent)
    }

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
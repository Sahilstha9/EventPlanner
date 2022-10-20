package com.example.eventplanner.View.Fragments

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eventplanner.R
import com.example.eventplanner.View.Activities.AddCategoryActivity
import com.example.eventplanner.View.Activities.AddEventActivity
import com.example.eventplanner.View.Adapters.CategoryListAdapter
import com.example.eventplanner.View.Adapters.DoneAdapter
import com.example.eventplanner.View.Fragments.BottomSheet.BottomSheetFragment
import com.example.eventplanner.View.Fragments.BottomSheet.CategoryBottomSheetFragment
import com.example.eventplanner.viewModel.CategoryViewModel
import com.example.eventplanner.viewModel.ListViewModel
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event

class CategoryListFragment : Fragment(R.layout.fragment_category_list) {
    private val TAG = "CalendarListFragment"
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


    fun editCategory(category: Category){
        categoryActivityIntent = Intent(context, AddCategoryActivity::class.java)
        categoryActivityIntent!!.putExtra("category", category)
        getCategory.launch(categoryActivityIntent)
    }

    fun deleteCategory(category: Category){
        viewModel.deleteCategory(category, requireView())
    }
}
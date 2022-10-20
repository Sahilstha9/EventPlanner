package com.example.eventplanner.viewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.categoryplanner.Modal.CategoryDatabase
import com.example.eventplanner.Modal.EventDatabase
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel(){

    private val TAG: String = "CategoryListModal"
    private var db = CategoryDatabase
    var categoryList : MutableLiveData<MutableList<Category>> = MutableLiveData<MutableList<Category>>()

    init {
        listenToCategory()
    }

    private fun listenToCategory() {
        db.getDatabaseRef().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(categories: DataSnapshot) {
                val data : MutableList<Category> = mutableListOf()
                if(categories.exists()){
                    for (e in categories.children){
                        val category = e.getValue(Category::class.java)
                        data.add(category!!)
                    }
                    categoryList.value = data
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun deleteCategory(category: Category, view: View){
        viewModelScope.launch {
            db.deleteCategory(category, view)
        }
    }

    fun updateCategory(category: Category, view: View){
        viewModelScope.launch {
            db.updateCategory(category, view)
        }
    }
}

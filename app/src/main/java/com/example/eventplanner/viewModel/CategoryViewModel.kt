package com.example.eventplanner.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplanner.modal.CategoryDatabase
import com.example.eventplanner.modal.EventDatabase
import com.example.eventplanner.viewModel.parcels.CategoriesWithEvent
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel(){

    private val TAG: String = "CategoryListModal"
    private var db = CategoryDatabase
    var cList = db.mCategoryList
    private var eventDb = EventDatabase
    var categoryList : MutableLiveData<MutableList<CategoriesWithEvent>> = MutableLiveData<MutableList<CategoriesWithEvent>>()

    init {
        getEvents()
    }

    fun getEvents(){
        val data = mutableListOf<CategoriesWithEvent>()
        for (i in cList.value ?: mutableListOf()){
            val cat = CategoriesWithEvent()
            cat.category = i
            Log.i(TAG, i.name)
            for (j in eventDb.mEventList.value!!){
                Log.i(TAG, j.category)
                if(i.id == j.category){
                    cat.events.add(j)
                }
            }
            data.add(cat)
        }
        categoryList.value = data
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

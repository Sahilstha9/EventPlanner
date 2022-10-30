package com.example.eventplanner.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplanner.modal.CategoryDatabase
import com.example.eventplanner.modal.EventDatabase
import com.example.eventplanner.viewModel.classes.CategoriesWithEvent
import com.example.eventplanner.viewModel.parcels.Category
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel(){

    private val TAG: String = "CategoryListModal"
    private var eventDb = EventDatabase
    var evenList = eventDb.mEventList
    private var db = CategoryDatabase
    var cList = db.mCategoryList
    var categoryList : MutableLiveData<MutableList<CategoriesWithEvent>> = MutableLiveData<MutableList<CategoriesWithEvent>>()

    init {
        getEvents()
    }

    fun getEvents(){
        val data = mutableListOf<CategoriesWithEvent>()
        for (i in cList.value ?: mutableListOf()){
            val cat = CategoriesWithEvent()
            cat.category = i
            for (j in evenList.value!!){
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

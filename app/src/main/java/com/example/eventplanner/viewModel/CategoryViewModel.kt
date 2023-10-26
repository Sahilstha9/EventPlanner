package com.example.eventplanner.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventplanner.repository.CategoryRepository
import com.example.eventplanner.repository.EventRepository
import com.example.eventplanner.viewModel.parcels.CategoriesWithEvent
import com.example.eventplanner.viewModel.parcels.Category
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel(){

    private val TAG: String = "CategoryListModal"
    private var eventDb = EventRepository
    var evenList = eventDb.getEventList()
    private var db = CategoryRepository
    var cList = db.getCategoryList()
    var categoryList : MutableLiveData<MutableList<CategoriesWithEvent>> = MutableLiveData<MutableList<CategoriesWithEvent>>()

    init {
        getEvents()
    }

    /**
     * updates the category list live data
     */
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

    /**
     * calls delete category from the database to delete the category passed
     */
    fun deleteCategory(category: Category, view: View){
        viewModelScope.launch {
            db.deleteCategory(category, view)
        }
    }

    /**
     * calls updates category from the database to update the category passed
     */
    fun updateCategory(category: Category, view: View){
        viewModelScope.launch {
            db.updateCategory(category, view)
        }
    }
}

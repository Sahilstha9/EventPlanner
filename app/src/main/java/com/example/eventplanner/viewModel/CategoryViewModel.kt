package com.example.eventplanner.viewModel

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
    private var eventDb = EventDatabase
    var categoryList : MutableLiveData<MutableList<CategoriesWithEvent>> = MutableLiveData<MutableList<CategoriesWithEvent>>()

    init {
        listenToCategory()
    }

    private fun listenToCategory() {
        db.getDatabaseRef().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(categories: DataSnapshot) {
                val data : MutableList<CategoriesWithEvent> = mutableListOf()
                if(categories.exists()){
                    for (e in categories.children){
                        val cat = CategoriesWithEvent()
                        val category = e.getValue(Category::class.java)
                        eventDb.getDatabaseRef().get().addOnSuccessListener {
                            val events = mutableListOf<Event>()
                            if(it.exists()){
                                for (i in it.children){
                                    val event = i.getValue(Event::class.java)
                                    if(event?.category == category?.id){
                                        events.add(event!!)
                                    }
                                }
                                cat.events = events
                                cat.category = category
                                data.add(cat)
                                categoryList.postValue(data)
                            }
                        }
                    }
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

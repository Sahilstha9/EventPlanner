package com.example.eventplanner.viewModel

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.categoryplanner.Modal.CategoryDatabase
import com.example.eventplanner.Modal.EventDatabase
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event
import java.util.*

class AddCategoryViewModel {
    private var db : CategoryDatabase = CategoryDatabase
    lateinit var name: String
    lateinit var description: String
    lateinit var id : String
    private var _categoryList = MutableLiveData<Category>()

    val category : LiveData<Category>
    get() = _categoryList

    fun inputValidation(name : TextView): Boolean {
        var isValid = true
        if(name.text.isEmpty()){
            name.error = "This field can not be empty"
            isValid = false
        }
        return isValid
    }
}
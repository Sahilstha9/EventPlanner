package com.example.eventplanner.viewModel

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventplanner.modal.AuthenticationModal
import com.example.eventplanner.modal.CategoryDatabase
import com.example.eventplanner.viewModel.parcels.Category

class AddCategoryViewModel : ViewModel(){
    private var auth = AuthenticationModal
    lateinit var name: String
    lateinit var description: String
    lateinit var id : String
    private var _categoryList = MutableLiveData<Category>()

    fun getUser() : String{
        return auth.getUser().value!!.uid
    }

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
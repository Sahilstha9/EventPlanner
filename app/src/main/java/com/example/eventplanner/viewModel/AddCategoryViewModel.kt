package com.example.eventplanner.viewModel

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventplanner.repository.AuthenticationRepository
import com.example.eventplanner.viewModel.parcels.Category

class AddCategoryViewModel : ViewModel(){
    private var auth = AuthenticationRepository
    lateinit var name: String
    lateinit var description: String
    lateinit var id : String
    private var _categoryList = MutableLiveData<Category>()

    fun getUser() : String{
        return auth.getUser().value!!.uid
    }

    val category : LiveData<Category>
    get() = _categoryList

    /**
     * validates the name of the category
     */
    fun inputValidation(name : TextView): Boolean {
        var isValid = true
        if(name.text.isEmpty()){
            name.error = "This field can not be empty"
            isValid = false
        }
        return isValid
    }
}
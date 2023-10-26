package com.example.eventplanner.viewModel

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eventplanner.repository.AuthenticationRepository
import com.example.eventplanner.repository.CategoryRepository
import com.example.eventplanner.viewModel.parcels.Category
import java.util.*

class AddEventViewModel : ViewModel(){

    val TAG = "AddEventViewModel"
    lateinit var name: String
    lateinit var date: Date
    lateinit var category: String
    lateinit var description: String
    var done: Boolean = false
    lateinit var id: String
    private var db = CategoryRepository
    private var auth = AuthenticationRepository
    var categoryList : LiveData<MutableList<Category>> = db.getCategoryList()

    fun getUser() : String{
        return auth.getUser().value!!.uid
    }

    /**
     * validates fields in the add event form
     */
    fun inputValidation(name : TextView, date: TextView, location : TextView, time: TextView) : Boolean{
        var isValid = true
        if(name.text.isEmpty()){
            name.error = "This field can not be empty"
            isValid = false
        }
        else if (!validNoun(name.text.toString())){
            name.error = "Invalid Characters Found"
            isValid = false
        }
        if(date.text.isEmpty()){
            date.error = "This field can not be empty"
            isValid = false
        }else if(!validDate(date.text.toString())){
            date.error = "Invalid format! Please enter in format(dd-mm-yyyy)"
            isValid = false
        }
        if (location.text.isEmpty()){
            location.error = "This field can not be empty"
            isValid = false
        }
        if(time.text.isEmpty()){
            time.error = "This field can not be empty"
            isValid = false
        }
        else if(!validTime(time.text.toString())){
            time.error = "Invalid Time Format! Please Enter in format (hh:mm)"
            isValid = false
        }
        return isValid
    }

    /**
     * checks if the date is valid
     */
    private fun validDate(dateStr: String?): Boolean {
        val re = Regex("(([1-9])|([1-2][0-9])|([3][0-1]))((\\/)|-)(([0]?[1-9])|([1][0-2]))((\\/)|-)20(([0-1]\\d)|(2[0-9]))")
        if(dateStr!!.matches(re)) return true
        return false
    }

    /**
     * checks if the string is valid
     */
    private fun validNoun(str : String) : Boolean{
        val re = Regex("^(?![\\s.]+\$)[a-zA-Z0-9\\s.]*\$")
        if(str.matches(re)) return true
        return false
    }

    /**
     * checks if time is valid
     */
    private fun validTime(time : String) : Boolean{
        val re = Regex("^(([0-1]?[0-9])|(2[0-3])):([0-5][0-9])$")
        if(time.matches(re)) return true
        return false
    }
}
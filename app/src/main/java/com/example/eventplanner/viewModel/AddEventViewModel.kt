package com.example.eventplanner.viewModel

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.categoryplanner.Modal.CategoryDatabase
import com.example.eventplanner.Modal.EventDatabase
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*

class AddEventViewModel : ViewModel(){

    val TAG = "AddEventViewModel"
    lateinit var name: String
    lateinit var date: Date
    lateinit var category: String
    lateinit var description: String
    lateinit var location: String
    var done: Boolean = false
    lateinit var id: String
    private var db = CategoryDatabase
    var categoryList : MutableLiveData<MutableList<Category>> = MutableLiveData<MutableList<Category>>()

    init {
        listenToCategory()
    }

    private fun listenToCategory() {
        db.getDatabaseRef().addValueEventListener(object : ValueEventListener{
            override fun onDataChange(category: DataSnapshot) {
                val data : MutableList<Category> = mutableListOf()
                if(category.exists()){
                    for (e in category.children){
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

    fun inputValidation(name : TextView, date: TextView, location : TextView, time: TextView) : Boolean{
        var isValid = true
        if(name.text.isEmpty()){
            name.setError("This field can not be empty")
            isValid = false
        }
        else if (!validNoun(name.text.toString())){
            name.setError("Invalid Characters Found")
            isValid = false
        }
        if(date.text.isEmpty()){
            date.setError("This field can not be empty")
            isValid = false
        }else if(!validDate(date.text.toString())){
            date.setError("Invalid format! Please enter in format(dd-mm-yyyy)")
            isValid = false
        }
        if (location.text.isEmpty()){
            location.setError("This field can not be empty")
            isValid = false
        }
        if(time.text.isEmpty()){
            time.setError("This field can not be empty")
            isValid = false
        }
        else if(!validTime(time.text.toString())){
            time.setError("Invalid Time Format! Please Enter in format (hh:mm)")
            isValid = false
        }
        return isValid
    }

    private fun validDate(dateStr: String?): Boolean {
        val re = Regex("(([0-2][0-9])|([3][0-1]))-(([0][0-9])|([1][0-2]))-20(([0-1]\\d)|(2[0-2]))")
        if(dateStr!!.matches(re)) return true
        return false
    }

    private fun validNoun(str : String) : Boolean{
        val re = Regex("^(?![\\s.]+\$)[a-zA-Z\\s.]*\$")
        if(str.matches(re)) return true
        return false
    }

    private fun validTime(time : String) : Boolean{
        val re = Regex("^(([0-1][0-9])|(2[0-3])):([0-5][0-9])$")
        if(time.matches(re)) return true
        return false
    }
}
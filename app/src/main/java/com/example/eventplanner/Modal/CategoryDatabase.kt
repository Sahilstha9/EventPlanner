package com.example.categoryplanner.Modal

import android.util.Log
import com.example.eventplanner.viewModel.parcels.Category
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CategoryDatabase {
    val TAG = "CategoryDatabase"
    private val db = FirebaseDatabase.getInstance().getReference("categories")
    private lateinit var categoryList : MutableList<Category>

    fun createCategory(category: Category){
        category.id = db.push().key!!

        db.child(category.id).setValue(category).addOnSuccessListener {
            Log.i(TAG, "1 document added to Category Collection")
        }.addOnFailureListener{
            Log.i(TAG, "Failure on adding categorys")
        }
    }

    fun getCategories() : MutableList<Category>{
        categoryList = mutableListOf()
        db.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(categories: DataSnapshot) {
                if (categories.exists()){
                    categoryList.clear()
                    for (e in categories.children){
                        val category = e.getValue(Category::class.java)
                        categoryList.add(category!!)
                        Log.i(TAG, category.name)
                    }
                    Log.i(TAG, categoryList.toString())
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return categoryList
    }

    fun updateCategory(category : Category){
        db.child(category.id).setValue(category).addOnSuccessListener {
            Log.i(TAG, "${category.id} Record Modified")
        }.addOnCanceledListener {
            Log.i(TAG, "${category.id} Record failed to be modified")
        }
    }

    fun deleteCategory(category : Category){
        db.child(category.id).removeValue().addOnSuccessListener {
            Log.i(TAG, "${category.id} Record Deleted")
        }.addOnCanceledListener {
            Log.i(TAG, "${category.id} Record Failed to be deleted")
        }
    }
}
package com.example.categoryplanner.Modal

import android.util.Log
import com.example.eventplanner.viewModel.ListViewModel
import com.example.eventplanner.viewModel.parcels.Category
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.database.*
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import java.util.Observable

object CategoryDatabase : Observable(){
    val TAG = "CategoryDatabase"
    fun getDatabaseRef() : DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("categories")
    }
    private var categoryList : MutableList<Category> = mutableListOf()

    fun createCategory(category: Category){
        category.id = getDatabaseRef().push().key!!
        getDatabaseRef().child(category.id).setValue(category).addOnSuccessListener {
            Log.i(TAG, "1 document added to Category Collection")
        }.addOnFailureListener{
            Log.i(TAG, "Failure on adding categories")
        }
    }

    fun getCategories() : MutableList<Category>{
        var toReturn : MutableList<Category> = mutableListOf()
        getDatabaseRef().addValueEventListener(object : ValueEventListener{
            override fun onDataChange(categories: DataSnapshot) {
                val data : MutableList<Category> = mutableListOf()
                if (categories.exists()){
                    for (e in categories.children){
                        val category = e.getValue(Category::class.java)
                        data.add(category!!)
                        Log.i(TAG, category.name)
                    }
                    toReturn = data
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return toReturn
    }


    fun updateCategory(category : Category){
        getDatabaseRef().child(category.id).setValue(category).addOnSuccessListener {
            Log.i(TAG, "${category.id} Record Modified")
        }.addOnCanceledListener {
            Log.i(TAG, "${category.id} Record failed to be modified")
        }
    }

    fun deleteCategory(category : Category){
        getDatabaseRef().child(category.id).removeValue().addOnSuccessListener {
            Log.i(TAG, "${category.id} Record Deleted")
        }.addOnCanceledListener {
            Log.i(TAG, "${category.id} Record Failed to be deleted")
        }
    }
}
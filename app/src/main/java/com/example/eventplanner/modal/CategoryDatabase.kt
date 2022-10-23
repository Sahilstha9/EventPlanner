package com.example.eventplanner.modal

import android.util.Log
import android.view.View
import com.example.eventplanner.viewModel.parcels.Category
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import java.util.Observable

object CategoryDatabase : Observable(){
    const val TAG = "CategoryDatabase"
    fun getDatabaseRef() : DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("categories")
    }

    fun createCategory(category: Category,view: View){
        category.id = getDatabaseRef().push().key!!
        Log.i(TAG, category.toString())
        getDatabaseRef().child(category.id).setValue(category).addOnSuccessListener {
            Snackbar.make(view, "Category has been successfully added", Snackbar.LENGTH_LONG).show()
            Log.i(TAG, "1 document added to Category Collection")
        }.addOnFailureListener{
            Log.i(TAG, "Failure on adding categories")
        }
    }


    fun updateCategory(category : Category, view: View){
        getDatabaseRef().child(category.id).setValue(category).addOnSuccessListener {
            Snackbar.make(view, "Category has been successfully updated", Snackbar.LENGTH_LONG).show()
            Log.i(TAG, "${category.id} Record Modified")
        }.addOnCanceledListener {
            Log.i(TAG, "${category.id} Record failed to be modified")
        }
    }

    fun deleteCategory(category : Category, view : View){
        getDatabaseRef().child(category.id).removeValue().addOnSuccessListener {
            Snackbar.make(view, "Category has been successfully deleted", Snackbar.LENGTH_LONG).show()
            Log.i(TAG, "${category.id} Record Deleted")
        }.addOnCanceledListener {
            Log.i(TAG, "${category.id} Record Failed to be deleted")
        }
    }
}
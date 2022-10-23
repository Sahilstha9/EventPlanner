package com.example.categoryplanner.Modal

import android.app.Activity
import android.util.Log
import android.view.View
import com.example.eventplanner.viewModel.ListViewModel
import com.example.eventplanner.viewModel.parcels.Category
import com.google.android.gms.tasks.Tasks.await
import com.google.android.material.snackbar.Snackbar
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
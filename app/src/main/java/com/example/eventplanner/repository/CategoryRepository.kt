package com.example.eventplanner.repository

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.eventplanner.viewModel.parcels.Category
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import java.util.*

/**
 * repository used for category
 */
object CategoryRepository : Observable(){
    const val TAG = "CategoryDatabase"
    private var auth = AuthenticationRepository
    var mCategoryList : MutableLiveData<MutableList<Category>> = MutableLiveData()

    fun getCategoryList(): LiveData<MutableList<Category>> {
        return mCategoryList
    }

    private fun getDatabaseRef() : DatabaseReference {
        return FirebaseDatabase.getInstance().getReference("categories")
    }

    init{
        retrieveCategoryList()
    }

    /**
     * creates category record in the database
     */
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

    /**
     * calls data on change to update live data field in real time retrieve
     */
    private fun retrieveCategoryList(){
        getDatabaseRef().orderByChild("userId").equalTo(auth.getUser().value?.uid).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(categories: DataSnapshot) {
                val data: MutableList<Category> = mutableListOf()
                if (categories.exists()) {
                    for (e in categories.children) {
                        val category = e.getValue(Category::class.java)
                        data.add(category!!)
                    }
                    mCategoryList.value = data
                    mCategoryList.postValue(data)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    /**
     * re initialises the live data
     */
    fun reInitList(){
        mCategoryList.value = null
    }

    /**
     * updates category record in the database and displays snack bar once updated
     */
    fun updateCategory(category : Category, view: View){
        getDatabaseRef().child(category.id).setValue(category).addOnSuccessListener {
            Snackbar.make(view, "Category has been successfully updated", Snackbar.LENGTH_LONG).show()
            Log.i(TAG, "${category.id} Record Modified")
        }.addOnCanceledListener {
            Log.i(TAG, "${category.id} Record failed to be modified")
        }
    }

    /**
     * deletes category record from the database and displays snack bar once deleted
     */
    fun deleteCategory(category : Category, view : View){
        getDatabaseRef().child(category.id).removeValue().addOnSuccessListener {
            Snackbar.make(view, "Category has been successfully deleted", Snackbar.LENGTH_LONG).show()
            Log.i(TAG, "${category.id} Record Deleted")
        }.addOnCanceledListener {
            Log.i(TAG, "${category.id} Record Failed to be deleted")
        }
    }
}
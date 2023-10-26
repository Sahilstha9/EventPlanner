package com.example.eventplanner.repository

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.*
import java.util.concurrent.Executors

/**
 * repository used for image
 */
object ImageRepository : Observable() {
    const val TAG = "ImageModal"

    private fun getDatabaseRef() : StorageReference {
        return FirebaseStorage.getInstance().reference
    }

    /**
     * uploads image received from parameter in the database as imageName (from parameter)
     */
    fun uploadImage(image : Uri?, imageName: String){
        if(image != null) {
            getDatabaseRef().child("images/$imageName").putFile(image).addOnSuccessListener {
                Log.i(TAG, "Image Uploaded")
            }
        }
    }

    /**
     * retrieves image from the database
     */
    fun getImage(v : ImageView, imageName: String){
        val BACKGROUND = Executors.newFixedThreadPool(2)
        BACKGROUND.submit{
            val localFile = File.createTempFile("tempImage", "jpeg")
            getDatabaseRef().child("images/$imageName").getFile(localFile).addOnSuccessListener{
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                v.setImageBitmap(bitmap)
            }
        }
    }

    /**
     * deletes image from the database
     */
    fun deleteImage(imageName: String){
        getDatabaseRef().child("images/$imageName").delete().addOnSuccessListener {
            Log.i(TAG, "Image deleted")
        }
    }
}
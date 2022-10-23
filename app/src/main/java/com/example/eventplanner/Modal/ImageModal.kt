package com.example.eventplanner.Modal

import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.util.*
import java.util.concurrent.Executors

object ImageModal : Observable() {
    val TAG = "ImageModal"

    fun getDatabaseRef() : StorageReference {
        return FirebaseStorage.getInstance().reference
    }

    fun uploadImage(image : Uri?, imageName: String){
        if(image != null) {
            getDatabaseRef().child("images/$imageName").putFile(image).addOnSuccessListener {
                Log.i(TAG, "Image Uploaded")
            }
        }
    }


    fun getImage(v : ImageView, imageName: String){
        val BACKGROUND = Executors.newFixedThreadPool(2)
        BACKGROUND.submit{
            val localFile = File.createTempFile("tempImage", "jpeg")
            getDatabaseRef().child("images/$imageName").getFile(localFile).addOnSuccessListener{
                Log.i(TAG, imageName)
                val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
                v.setImageBitmap(bitmap)
            }
        }
    }
}
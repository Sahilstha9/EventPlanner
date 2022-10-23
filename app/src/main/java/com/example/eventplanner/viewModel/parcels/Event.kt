package com.example.eventplanner.viewModel.parcels
import android.os.Parcelable
import android.util.Log
import com.google.firebase.database.DataSnapshot
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.HashMap

@Parcelize
class Event(var name : String, var date : Date, var category : String, var description: String, var location : String, var done : Boolean, var imageLoc : String = "", var id : String = "") : Parcelable{
    constructor() : this("", Date(), "", "", "", false) {

    }
}
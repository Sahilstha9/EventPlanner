package com.example.eventplanner.viewModel.parcels
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
class Event(var name : String, var date : Date, var category : String, var description: String, var location : String, var done : Boolean, var id : String = "") : Parcelable{
    constructor() : this("", Date(), "", "", "", false) {

    }
}
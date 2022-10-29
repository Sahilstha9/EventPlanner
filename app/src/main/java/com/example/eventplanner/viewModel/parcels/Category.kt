package com.example.eventplanner.viewModel.parcels

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Category(var name : String, var description: String, var id : String = "", var userId : String ) : Parcelable{
    constructor(): this ("", "", userId = ""){

    }
}
package com.example.eventplanner.ViewModel
import android.accounts.AuthenticatorDescription
import android.location.Location
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Event(var name : String, var date : Date, var category : String, var description: String, var location : String ) : Parcelable
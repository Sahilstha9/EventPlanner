package com.example.eventplanner.Modal

import android.accounts.AuthenticatorDescription
import android.location.Location
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Category(var name : String, var description: String ) : Parcelable
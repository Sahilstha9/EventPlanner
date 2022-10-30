package com.example.eventplanner.viewModel.classes

import android.os.Parcelable
import com.example.eventplanner.viewModel.parcels.Category
import com.example.eventplanner.viewModel.parcels.Event
import kotlinx.parcelize.Parcelize

@Parcelize
class CategoriesWithEvent(var category: Category?, var events : MutableList<Event>) : Parcelable{
    constructor() : this(null, mutableListOf()){

    }
}
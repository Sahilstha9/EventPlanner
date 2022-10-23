package com.example.eventplanner.view

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlin.collections.ArrayList

class MyXAxisFormatter : ValueFormatter() {
    private lateinit var days : ArrayList<String>
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return days.getOrNull(value.toInt()) ?: value.toString()
    }

    fun setArray(list : java.util.ArrayList<String>){
        days = list
    }
}
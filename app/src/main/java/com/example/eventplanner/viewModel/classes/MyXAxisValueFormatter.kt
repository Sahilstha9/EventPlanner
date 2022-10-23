package com.example.eventplanner.View

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DateFormat
import java.text.DecimalFormat
import java.util.*
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
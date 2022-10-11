package com.example.eventplanner.View

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.DateFormat
import java.util.*

class MyXAxisValueFormatter: IndexAxisValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        val emissionsMilliSince1970Time = value.toLong() * 1000

        // Show time in local version

        // Show time in local version
        val timeMilliseconds = Date(emissionsMilliSince1970Time)
        val dateTimeFormat: DateFormat =
            DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())

        return dateTimeFormat.format(timeMilliseconds)
    }
}
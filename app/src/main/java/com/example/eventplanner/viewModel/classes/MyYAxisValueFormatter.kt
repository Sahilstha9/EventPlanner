package com.example.eventplanner.viewModel.classes

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter

class MyYAxisValueFormatter : ValueFormatter() {

    // override this for custom formatting of XAxis or YAxis labels
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return value.toInt().toString()
    }
}
package com.example.eventplanner.View.Fragments

import android.graphics.Color.red
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.eventplanner.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class InsightsFragment : Fragment(R.layout.fragment_insights) {

    private lateinit var lineChart : LineChart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lineChart = view.findViewById(R.id.lineChart)
        //Part1
        val entries = ArrayList<Entry>()
//        var xais : XAxis = lineChart.xAxis
//        xais.position = XAxis.XAxisPosition.BOTTOM
//        xais.valueFormatter =

//Part2
        entries.add(Entry(2f, 2f))
        entries.add(Entry(3f, 7f))
        entries.add(Entry(4f, 20f))
        entries.add(Entry(5f, 16f))

//Part3
        val vl = LineDataSet(entries, "My Type")

//Part4
        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.fillColor = R.color.done
        vl.fillAlpha = R.color.missed

//Part5
        lineChart.xAxis.labelRotationAngle = 0f

//Part6
        lineChart.data = LineData(vl)

////Part7
//        lineChart.axisRight.isEnabled = false
//        lineChart.xAxis.axisMaximum = j+0.1f
//
////Part8
//        lineChart.setTouchEnabled(true)
//        lineChart.setPinchZoom(true)
//
////Part9
//        lineChart.description.text = "Days"
//        lineChart.setNoDataText("No forex yet!")
//
////Part10
//        lineChart.animateX(1800, Easing.EaseInExpo)
////
//////Part11
//        val markerView = CustomMarker(this@ShowForexActivity, R.layout.marker_view)
//        lineChart.marker = markerView
    }
}
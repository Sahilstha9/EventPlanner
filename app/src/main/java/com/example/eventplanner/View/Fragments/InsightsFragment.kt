package com.example.eventplanner.View.Fragments

import android.graphics.Color.red
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.eventplanner.R
import com.example.eventplanner.View.MyXAxisFormatter
import com.example.eventplanner.viewModel.InsightsViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class InsightsFragment : Fragment(R.layout.fragment_insights) {

    private lateinit var lineChart : LineChart
    private lateinit var barChart: BarChart
    private lateinit var dataVal: MutableList<BarEntry>
    private val viewModel = InsightsViewModel()
    private val valueFormatter = MyXAxisFormatter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        barChart = view.findViewById(R.id.barChart)

        listObserver(viewModel.completedCount)
        listObserver(viewModel.upcomingCount)
        listObserver(viewModel.missedCount)
    }

    fun listObserver(list : MutableLiveData<Int>){
        list.observe(viewLifecycleOwner, Observer {
            valueFormatter.setArray(arrayListOf("completed", "upcoming", "missed"))
            dataVal = arrayListOf()
            dataVal.add(BarEntry("0".toFloat(), viewModel.completedCount.value?.toFloat() ?: "3".toFloat()))
            dataVal.add(BarEntry("1".toFloat(), viewModel.upcomingCount.value?.toFloat() ?: "5".toFloat()))
            dataVal.add(BarEntry("2".toFloat(), viewModel.missedCount.value?.toFloat() ?: "6".toFloat()))
            val barDataSet = BarDataSet(dataVal, "DataSet1")
            barDataSet.valueFormatter = valueFormatter
            val barData = BarData(barDataSet)
            barChart.xAxis.valueFormatter = valueFormatter
            barChart.data = barData
            barChart.invalidate()
        })
    }

}